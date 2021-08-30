package org.upgrad.doctorservice.service.ses;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
@Component
public class SesEmailVerificationService {

    private SesClient sesClient;
    private final FreeMarkerConfigurer configurer;
    private String fromEmail = "sharma.rahul22012@gmail.com";//needs to be a verified email id

    @Value("${aws.access_key_id}")
    private String accessKey;

    @Value("${aws.secret_access_key}")
    private String secretKey;

    @PostConstruct
    public void init(){
        // When you hit the endpoint to verify the email this needs to be the access key for your AWS account
        // When you hit the endpoint to send an email this value needs to be updated to the Smtp username that you generated
        //accessKey="AKIA44TR2NQA7UNBVCM6";


        // When you hit the endpoint to verify the email this needs to be the secret key for your AWS account
        // When you hit the endpoint to send an email this value needs to be updated to the Smtp password that you generated
        //secretKey="BOY50jFhcn7K4Mozkf+2TAzlWaKOJXxpp+qIZT1XUZHz";//
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(accessKey,secretKey));
        sesClient = SesClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.US_EAST_1)
                .build();
    }

    public void verifyEmail(String emailId){
        sesClient.verifyEmailAddress(req->req.emailAddress(emailId));
    }

    public void sendEmail(DoctorDto doctorDto) throws IOException, TemplateException, MessagingException {
        Map<String,Object> templateModel = new HashMap<>();
        templateModel.put("doctorDto",doctorDto);
        Template freeMarkerTemplate = configurer.getConfiguration().getTemplate("userwelcome.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerTemplate,templateModel);
        sendSimpleMessage(doctorDto.getEmailId(),"Welcome Email",htmlBody);
    }

    private void sendSimpleMessage(String toEmail, String subject, String body) throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.port",587);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth","true");
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(fromEmail);
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        Transport transport = session.getTransport();
        try {
            transport.connect("email-smtp.us-east-1.amazonaws.com", accessKey, secretKey);
            transport.sendMessage(msg, msg.getAllRecipients());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            transport.close();
        }
    }


}
