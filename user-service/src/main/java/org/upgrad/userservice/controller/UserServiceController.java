package org.upgrad.userservice.controller;

import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.userservice.exceptions.RecordNotFoundException;
import org.upgrad.userservice.model.dto.UserServiceDto;
import org.upgrad.userservice.model.entity.UserInfoEntity;
import org.upgrad.userservice.service.aws.AWSS3Service;
import org.upgrad.userservice.service.userService.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserServiceController {

    UserService userService;
    AWSS3Service awss3Service;

    @Autowired
    public UserServiceController(UserService userService, AWSS3Service awss3Service) {
        this.userService = userService;
        this.awss3Service = awss3Service;
    }

    /**
     *
     * @param userServiceDto:  requested data firstName, lastName, dob, emailID, mobile.
     * @return UserInfoEntity: after saving the records in DB
     */
    @SneakyThrows
    @PostMapping("/users")
    public ResponseEntity<UserInfoEntity> doctorRegistration(@Valid @RequestBody UserServiceDto userServiceDto){
        UserInfoEntity userInfo = this.userService.userRegistration(userServiceDto);
        return new ResponseEntity<UserInfoEntity>(userInfo, HttpStatus.CREATED);
    }

    /**
    This endpoint is responsible for collecting information about the user.
    URI: /users/{userID}
    HTTP method: GET
    Role: USER, ADMIN.
     @param userID: to find the corresponding result of userID
     */
    @GetMapping(value = "/users/{userID}")
    public ResponseEntity<UserInfoEntity> getDoctorById(@PathVariable String userID) throws RecordNotFoundException {
        var obj = this.userService.getUser(userID);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    /**
     *
     * @param id : User id for which user you are uploading the files
     * @param files : files you wanted to upload on S3
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/users/{id}/documents")
    public ResponseEntity<String> uploadFiles(@PathVariable("id") String id, @RequestParam MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            awss3Service.uploadFile(id, file);
        }
        final String response = "Files(s) uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}