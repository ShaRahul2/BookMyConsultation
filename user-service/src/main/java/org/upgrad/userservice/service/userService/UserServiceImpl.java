package org.upgrad.userservice.service.userService;

import freemarker.template.TemplateException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.userservice.config.AppConfig;
import org.upgrad.userservice.dao.UserServiceDao;
import org.upgrad.userservice.exceptions.RecordNotFoundException;
import org.upgrad.userservice.model.dto.UserServiceDto;
import org.upgrad.userservice.model.entity.UserInfoEntity;
import org.upgrad.userservice.model.mapper.UserMapper;
import org.upgrad.userservice.service.ses.SesEmailVerificationService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    UserServiceDao userServiceDao;
    RestTemplate restTemplate;
    SesEmailVerificationService verifyEmail;

    @Autowired
    public UserServiceImpl(UserServiceDao userServiceDao, RestTemplate restTemplate, SesEmailVerificationService verifyEmail) {
        this.userServiceDao = userServiceDao;
        this.restTemplate = restTemplate;
        this.verifyEmail = verifyEmail;
    }

    /**
     *
     * @param userDto: requested data firstName, lastName, dob, emailID, mobile.
     * @return UserInfoEntity: after saving the records in DB
     */
   public UserInfoEntity userRegistration(UserServiceDto userDto){
       var userInfo = UserMapper.convertDTOToEntity(userDto);
       if (userInfo.getCreatedDate() == null) {
           userInfo.setCreatedDate(DateTime.now().toDate());
       }
       UUID uuid = UUID.randomUUID();
       userInfo.setId(uuid.toString());
       var sb = userServiceDao.save(userInfo);

       try {
           // To verify the email address first
           verifyEmail.verifyEmail(sb.getEmailId());

           // To send the email to the doctor
           verifyEmail.sendEmail(userDto);

            /*
            Sending kafka messages to the admin team
             */
           runProducer(1, userInfo.toString());
       } catch (InterruptedException e) {
           e.printStackTrace(); //Internally throw 500 error
       } catch (Exception e) {
           e.printStackTrace();
       }

       return sb;
   }

    public UserInfoEntity getUser(String userID) throws RecordNotFoundException {
        var sb = userServiceDao.findById(userID);
        //verifyEmail.verifyEmail(sb.getEmailId());
        //verifyEmail.sendEmail(userDto);
        return sb.orElseThrow(() -> new RecordNotFoundException(""));
    }


    /**
     * This help us to produce the Kafka Messages on EC2 kafka server
     *
     * @param sendMessageCount is used to set the iteration for the message, means how many time you wanted this message to be
     *                         repeated
     * @param message:         user registration info entity toString details will be passed in this as a successfull response after updating the
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
