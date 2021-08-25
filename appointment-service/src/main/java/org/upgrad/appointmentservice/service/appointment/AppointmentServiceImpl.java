package org.upgrad.appointmentservice.service.appointment;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
import org.upgrad.appointmentservice.model.mapper.PrescriptionMapper;
import org.upgrad.appointmentservice.service.ses.SesEmailVerificationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    AppointmentDao appointmentDao;
    RestTemplate restTemplate;
    SesEmailVerificationService verifyEmail;
    AvailabilityDao availabilityDao;
    PrescriptionDao prescriptionDao;

    @Value("http://localhost:8081/")
    private String doctorServiceUrl;

    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao, AvailabilityDao availabilityDao, RestTemplate restTemplate, SesEmailVerificationService verifyEmail, PrescriptionDao prescriptionDao) {
        this.appointmentDao = appointmentDao;
        this.restTemplate = restTemplate;
        this.verifyEmail = verifyEmail;
        this.availabilityDao = availabilityDao;
        this.prescriptionDao = prescriptionDao;
    }

    public String addAvailability(AvailabilityDto availabilityDto) throws ParseException {

        List<AvailabilityInfoEntity> list = new ArrayList<>();
        for (var obj : availabilityDto.getAvailabilityMap().entrySet()) {
            for (var val : obj.getValue()) {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(obj.getKey().toString());
                availabilityDto.setAvailability_date(date1);
                availabilityDto.setTime_slot(val);
                availabilityDto.setIs_booked(0);
                list.add(AvailabilityMapper.convertDTOToEntity(availabilityDto));
            }
        }
        availabilityDao.saveAll(list);
        return "Completed";
    }

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

    public String bookAppointment(AppointmentDto appointmentDto) throws ParseException {
        var mapper = AppointmentMapper.convertDTOToEntity(appointmentDto);
        UUID uuid = UUID.randomUUID();
        mapper.setAppointmentid(uuid.toString());
        mapper.setStatus("PendingPayment");
        mapper.setCreateddate(DateTime.now());

        String input = appointmentDto.getAppointmentDate().toString();//"Mon Jun 18 00:00:00 IST 2012";
        DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z uuuu")
                .withLocale(Locale.US);
        ZonedDateTime zdt = ZonedDateTime.parse(input, f);
        LocalDate ld = zdt.toLocalDate();
        DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        String output = ld.format(fLocalDate);
        System.out.println("output: " + output);

        //Date avDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDto.getAppointmentDate().toString());
        var availabilityInfo = availabilityDao.findByDoctoridAndAvailabilitydateAndTimeslot(
                appointmentDto.getDoctorId(), output, appointmentDto.getTimeSlot());
        if (availabilityInfo != null) {
            availabilityInfo.setIs_booked(1);
            availabilityDao.save(availabilityInfo);


            var appointmentInfo = appointmentDao.save(mapper);

            return appointmentInfo.getAppointmentid();
        }
        return "";
    }

    public AppointmentInfoEntity getAppointment(String appointmentId) {
        var obj = appointmentDao.findById(appointmentId);
        return obj.orElseThrow(() -> new RecordNotFoundException(""));
    }

    public List<AppointmentInfoEntity> getAppointmentsbyUser(String userId) {
        var obj = appointmentDao.findByUserid(userId);
        return obj;
    }

    public void savePrescription(PrescriptionDto prescriptionDto) {

        var appStatus = getAppointment(prescriptionDto.getAppointmentId());
        if (appStatus.getStatus().equalsIgnoreCase("PendingPayment")) {
            throw new PendingPaymentException("");
        }
        var mapped = PrescriptionMapper.convertDTOToEntity(prescriptionDto);
        DoctorDto dto = new DoctorDto();
        String url = this.doctorServiceUrl + "/doctors/" + prescriptionDto.getDoctorId();
        var doc = restTemplate.getForEntity(url, dto.getClass());
        var aa = prescriptionDao.save(mapped);

    }

    public void updateAppointment(String appointmentId) {
        var appData = appointmentDao.findById(appointmentId);

        if(appData.isPresent()) {
            appData.get().setStatus("Confirmed");
        }
    }
}