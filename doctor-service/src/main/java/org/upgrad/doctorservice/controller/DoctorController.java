package org.upgrad.doctorservice.controller;

import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.service.aws.AWSS3Service;
import org.upgrad.doctorservice.service.doctor.DoctorService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class DoctorController {

    DoctorService doctorService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @SneakyThrows
    @PostMapping("/doctors")
    public ResponseEntity<DoctorInfoEntity> doctorRegistration(@Valid @RequestBody DoctorDto doctorDto) throws TemplateException, MessagingException, IOException {
        DoctorInfoEntity doctorInfo = this.doctorService.doctorRegistration(doctorDto);
        return new ResponseEntity<DoctorInfoEntity>(doctorInfo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
        awss3Service.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
