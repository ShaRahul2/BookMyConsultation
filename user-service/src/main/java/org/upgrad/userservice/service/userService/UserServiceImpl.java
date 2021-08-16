package org.upgrad.userservice.service.userService;

import freemarker.template.TemplateException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.userservice.dao.UserServiceDao;
import org.upgrad.userservice.exceptions.RecordNotFoundException;
import org.upgrad.userservice.model.dto.UserServiceDto;
import org.upgrad.userservice.model.entity.UserInfoEntity;
import org.upgrad.userservice.model.mapper.UserMapper;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{

    UserServiceDao userServiceDao;
    RestTemplate restTemplate;
    //SesEmailVerificationService verifyEmail;

    @Autowired
    public UserServiceImpl(UserServiceDao userServiceDao, RestTemplate restTemplate) {
        this.userServiceDao = userServiceDao;
        this.restTemplate = restTemplate;
        //this.verifyEmail = verifyEmail;
    }

   public UserInfoEntity userRegistration(UserServiceDto userDto) throws TemplateException, IOException, MessagingException{
       var userInfo = UserMapper.convertDTOToEntity(userDto);
       if(userInfo.getCreatedDate() == null){
           userInfo.setCreatedDate(DateTime.now().toDate());
       }
       var sb = userServiceDao.save(userInfo);
       //verifyEmail.verifyEmail(sb.getEmailId());
       //verifyEmail.sendEmail(userDto);
       return sb;
   }

    public UserInfoEntity getUser(String userID) throws RecordNotFoundException {
        var sb = userServiceDao.findById(userID);
        //verifyEmail.verifyEmail(sb.getEmailId());
        //verifyEmail.sendEmail(userDto);
        return sb.orElseThrow(() -> new RecordNotFoundException(""));
    }
}
