package org.upgrad.userservice.service.userService;

import freemarker.template.TemplateException;
import org.upgrad.userservice.exceptions.RecordNotFoundException;
import org.upgrad.userservice.model.dto.UserServiceDto;
import org.upgrad.userservice.model.entity.UserInfoEntity;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {


    /**
     * Saves customer personal details.
     *
     * @param userDto
     * @return
     */
    UserInfoEntity userRegistration(UserServiceDto userDto) throws TemplateException, IOException, MessagingException;

    UserInfoEntity getUser(String userId) throws RecordNotFoundException;
}
