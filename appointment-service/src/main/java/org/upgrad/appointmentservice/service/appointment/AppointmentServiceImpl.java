package org.upgrad.appointmentservice.service.appointment;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.appointmentservice.config.AppConfig;
import org.upgrad.appointmentservice.dao.AppointmentDao;
import org.upgrad.appointmentservice.dao.AvailabilityDao;
import org.upgrad.appointmentservice.dao.PrescriptionDao;
import org.upgrad.appointmentservice.exceptions.PendingPaymentException;
import org.upgrad.appointmentservice.exceptions.RecordNotFoundException;
import org.upgrad.appointmentservice.model.dto.*;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;
import org.upgrad.appointmentservice.model.entity.AvailabilityInfoEntity;
import org.upgrad.appointmentservice.model.mapper.AppointmentMapper;
import org.upgrad.appointmentservice.model.mapper.AvailabilityMapper;
import org.upgrad.appointmentservice.model.mapper.PaymentRespMapper;
import org.upgrad.appointmentservice.model.mapper.PrescriptionMapper;
import org.upgrad.appointmentservice.service.ses.SesEmailVerificationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    AppointmentDao appointmentDao;
    RestTemplate restTemplate;
    SesEmailVerificationService verifyEmail;
    AvailabilityDao availabilityDao;
    PrescriptionDao prescriptionDao;

    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao, AvailabilityDao availabilityDao, RestTemplate restTemplate, SesEmailVerificationService verifyEmail, PrescriptionDao prescriptionDao) {
        this.appointmentDao = appointmentDao;
        this.restTemplate = restTemplate;
        this.verifyEmail = verifyEmail;
        this.availabilityDao = availabilityDao;
        this.prescriptionDao = prescriptionDao;
    }

    /**
     *  Adding availability for the doctors
     * @param availabilityDto
     * @param doctorId
     * @return
     * @throws ParseException
     */
    public String addAvailability(AvailabilityDto availabilityDto, String doctorId) throws ParseException {

        List<AvailabilityInfoEntity> list = new ArrayList<>();
        for (var obj : availabilityDto.getAvailabilityMap().entrySet()) {
            for (var val : obj.getValue()) {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(obj.getKey().toString());
                availabilityDto.setAvailability_date(date1);
                availabilityDto.setTime_slot(val);
                availabilityDto.setIs_booked(0);
                availabilityDto.setDoctorid(doctorId);
                list.add(AvailabilityMapper.convertDTOToEntity(availabilityDto));
            }
        }
        availabilityDao.saveAll(list);
        return "Completed";
    }

    /**
     * fetching doctor availability result filtered by DoctorId
     * @param doctorId
     * @return AvailabilityResponse: if the results is found
     * @throws ParseException
     */
    public AvailabilityResponse getAvailability(String doctorId) throws ParseException {
        AvailabilityResponse response = new AvailabilityResponse();
        var distinctByAvailability_date = availabilityDao.findDistinctByAvailabilitydate(doctorId);
        Map<Date, List<String>> availabilityMap = new IdentityHashMap<>();

        response.setDoctorId(doctorId);
        for (var disObj : distinctByAvailability_date) {
            var obj = availabilityDao.findByAvailabilitydate(disObj.getAvailabilitydate());
            availabilityMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(disObj.getAvailabilitydate().toString()), obj);
        }
        response.setAvailabilityMap(availabilityMap);
        return response;
    }

    /**
     * Booking appointment and update check the Map data then add it in DB accordingly
     * @param appointmentDto
     * @return
     * @throws ParseException: exception may occured while parsing the date
     */
    public String bookAppointment(AppointmentDto appointmentDto) throws ParseException {
        var mapper = AppointmentMapper.convertDTOToEntity(appointmentDto);
        UUID uuid = UUID.randomUUID();
        mapper.setAppointmentid(uuid.toString());
        mapper.setStatus("PendingPayment");
        mapper.setCreateddate(DateTime.now().toDate());

        // Parsing the Date as its comping in different format so to fetch the result from availability DB did the parsing the date
        String input = appointmentDto.getAppointmentDate().toString();//"Mon Jun 18 00:00:00 IST 2012";
        DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z uuuu")
                .withLocale(Locale.US);
        ZonedDateTime zdt = ZonedDateTime.parse(input, f);
        LocalDate ld = zdt.toLocalDate();
        DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        String output = ld.format(fLocalDate);
        System.out.println("output: " + output);

        // This will fetch the records from availability db on the basis of doctor id and booking status
        var availabilityInfo = availabilityDao.findByDoctoridAndAvailabilitydateAndTimeslot(
                appointmentDto.getDoctorId(), output, appointmentDto.getTimeSlot());
        if (availabilityInfo != null) {
            availabilityInfo.setIs_booked(1);
            // Update Availability db and setting Isbooked = 1 i.e. doctor is booked
            availabilityDao.save(availabilityInfo);


            var appointmentInfo = appointmentDao.save(mapper);

            return appointmentInfo.getAppointmentid();
        }
        return "";
    }

    /**
     * Fetch the appointment entity info filtered by appointmentid
     * @param appointmentId
     * @return
     */
    public AppointmentInfoEntity getAppointment(String appointmentId) {
        var obj = appointmentDao.findById(appointmentId);
        return obj.orElseThrow(() -> new RecordNotFoundException(""));
    }

    /**
     * return the list of appointments filtering by Userid
     * @param userId
     * @return
     */
    public List<AppointmentInfoEntity> getAppointmentsbyUser(String userId) {
        var obj = appointmentDao.findByUserid(userId);
        return obj;
    }

    /**
     * To save the prescription in Mongo Db
     *     The prescription can be sent only if the status of the appointment is Confirmed
     *     if the status of the appointment is set to ‘PendingPayment’ then returning with appropriate response message
     * @param prescriptionDto
     */
    public void savePrescription(PrescriptionDto prescriptionDto) {

        var appStatus = getAppointment(prescriptionDto.getAppointmentId());
        if (appStatus.getStatus().equalsIgnoreCase("PendingPayment")) {
            throw new PendingPaymentException("");
        }
        var mapped = PrescriptionMapper.convertDTOToEntity(prescriptionDto);
        DoctorDto dto = new DoctorDto();
        var aa = prescriptionDao.save(mapped);

    }

    /**
     * change appointment status to confirmed if the payment is made
     * @param appointmentId
     * @return
     */
    public PaymentResponse updateAppointment(String appointmentId) {
        var appData = appointmentDao.findById(appointmentId);


        if (appData.isPresent()) {
            var _appointmentInfo = appData.get();
            _appointmentInfo.setStatus("Confirmed");
            appointmentDao.save(_appointmentInfo);
            var obj = PaymentRespMapper.convertEntityToPaymentResp(_appointmentInfo);
            UUID uuid = UUID.randomUUID();
            obj.setId(uuid.toString());
            return obj;
        }
        return null;
    }

    /**
     * This help us to produce the Kafka Messages on EC2 kafka server
     *
     * @param sendMessageCount is used to set the iteration for the message, means how many time you wanted this message to be
     *                         repeated
     * @param message:         appointment entity info toString details will be passed in this as a successfull response after updating the
     *                         transaction id in Booking Table
     **/
    public static void runProducer(final int sendMessageCount, String message) throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);
        try (Producer<Long, String> producer = AppConfig.createProducer()) {
            long time = System.currentTimeMillis();
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>(AppConfig.TOPIC, index, message);
                producer.send(record, (metadata, exception) -> {
                    long elapsedTime = System.currentTimeMillis() - time;
                    if (metadata != null) {
                        System.out.printf("sent record(key=%s value=%s) " +
                                        "meta(partition=%d, offset=%d) time=%d\n",
                                record.key(), record.value(), metadata.partition(),
                                metadata.offset(), elapsedTime);
                    } else {
                        exception.printStackTrace();
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await(25, TimeUnit.SECONDS);
        }
    }
}