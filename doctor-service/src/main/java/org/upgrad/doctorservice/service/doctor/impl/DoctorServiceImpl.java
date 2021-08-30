package org.upgrad.doctorservice.service.doctor.impl;

import freemarker.template.TemplateException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.doctorservice.config.AppConfig;
import org.upgrad.doctorservice.dao.DoctorDao;
import org.upgrad.doctorservice.exceptions.CustomException;
import org.upgrad.doctorservice.exceptions.RecordNotFoundException;
import org.upgrad.doctorservice.exceptions.handler.CustomExceptionHandler;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.dto.UpdateDoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.model.mapper.DoctorMapper;
import org.upgrad.doctorservice.service.doctor.DoctorService;
import org.upgrad.doctorservice.service.ses.SesEmailVerificationService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    DoctorDao doctorDao;
    RestTemplate restTemplate;
    SesEmailVerificationService verifyEmail;

    @Autowired
    public DoctorServiceImpl(DoctorDao doctorDao, RestTemplate restTemplate, SesEmailVerificationService verifyEmail) {
        this.doctorDao = doctorDao;
        this.restTemplate = restTemplate;
        this.verifyEmail = verifyEmail;
    }

    /**
     * to register the doctor with default pending status
     * @param doctorRequest: contain the doctor data as request body parameter
     * @return DoctorInfoEntity: after successfully saving return doctor entity saved record with Doctor id
     */
    public DoctorInfoEntity doctorRegistration(DoctorDto doctorRequest) {
        var doctorInfo = DoctorMapper.convertDTOToEntity(doctorRequest);
        if (doctorInfo.getSpeciality() == null) {
            doctorInfo.setSpeciality("GENERAL_PHYSICIAN");
        }
        if (doctorInfo.getStatus() == null) {
            doctorInfo.setStatus("Pending");
        }
        if (doctorInfo.getRegistrationDate() == null) {
            doctorInfo.setRegistrationDate(DateTime.now().toDate());
        }

        // Generating doctor id with the help if UUID randomUUID method.
        UUID uuid = UUID.randomUUID();
        doctorInfo.setId(uuid.toString());

        var sb = doctorDao.save(doctorInfo);


        try {
            // To verify the email address first
            verifyEmail.verifyEmail(sb.getEmailId());

            // To send the email to the doctor
            verifyEmail.sendEmail(doctorRequest);

            /*
            Sending kafka messages to the admin team
             */
            runProducer(1, doctorInfo.toString());
        } catch (InterruptedException e) {
            e.printStackTrace(); //Internally throw 500 error
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * To update the doctor status
     * @param doctorId
     * @param Status: approve/rejected
     * @param updateDoctorDto : admin comments and admin name
     * @return
     * @throws TemplateException
     * @throws MessagingException
     * @throws IOException
     */
    public ResponseEntity<DoctorInfoEntity> doctorUpdate(String doctorId, String Status, UpdateDoctorDto updateDoctorDto) throws TemplateException, MessagingException, IOException {
        var doctorInfo = doctorDao.findById(doctorId);
        if (doctorInfo.isPresent()) {
            DoctorInfoEntity _doctorInfo = doctorInfo.get();
            _doctorInfo.setApprovedBy(updateDoctorDto.getApprovedBy());
            _doctorInfo.setApproverComments(updateDoctorDto.getApproverComments());
            _doctorInfo.setStatus(Status);
            _doctorInfo.setVerificationDate(DateTime.now().toDate());

            // To send the email to the doctor
            verifyEmail.sendEmail(DoctorMapper.convertEntityToDTO(_doctorInfo));

            /*
            Sending kafka messages to the admin team
             */
            try {
                runProducer(1, doctorInfo.toString());
            } catch (InterruptedException e) {
                e.printStackTrace(); //Internally throw 500 error
            }
            return new ResponseEntity<>(doctorDao.save(_doctorInfo), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get doctor list with the filters records basis on the Status or Speciality
     * @param status: active/pending
     * @param speciality: general physician
     * @return
     */
    public List<DoctorInfoEntity> getDoctorByStatus(String status, String speciality) {
        List<DoctorInfoEntity> obj = new ArrayList<DoctorInfoEntity>();
        if (speciality == null && status != null) {
            obj = doctorDao.findByStatus(status).stream().limit(20).collect(Collectors.toList());
        } else if (speciality != null && status == null) {
            obj = doctorDao.findBySpeciality(speciality).stream().limit(20).collect(Collectors.toList());
        } else {
            obj = doctorDao.findByStatusAndSpeciality(status, speciality).stream().limit(20).collect(Collectors.toList());
        }
        return obj;
    }

    /**
     * Get Doctor details by doctor id
     * @param doctorId : fetch the requested doctor details for Mongo Db
     * @return DoctorInfoEntity
     * @throws RecordNotFoundException : if the no records exists then throw custom exception
     */
    public DoctorInfoEntity getDoctorById(String doctorId) throws RecordNotFoundException {
        var obj = doctorDao.findById(doctorId);
        return obj.orElseThrow(() -> new RecordNotFoundException(""));
    }

    /**
     * This help us to produce the Kafka Messages on EC2 kafka server
     *
     * @param sendMessageCount is used to set the iteration for the message, means how many time you wanted this message to be
     *                         repeated
     * @param message:         Doctorbookingentityinfo toString details will be passed in this as a successfull response after updating the
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