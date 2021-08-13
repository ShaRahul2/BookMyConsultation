package org.upgrad.doctorservice.controller;

import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.upgrad.doctorservice.exceptions.RecordNotFoundException;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.dto.UpdateDoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.service.aws.AWSS3Service;
import org.upgrad.doctorservice.service.doctor.DoctorService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
public class DoctorController {

    DoctorService doctorService;

    AWSS3Service awss3Service;

    @Autowired
    public DoctorController(DoctorService doctorService, AWSS3Service awss3Service) {
        this.doctorService = doctorService;
        this.awss3Service = awss3Service;
    }

    @SneakyThrows
    @PostMapping("/doctors")
    public ResponseEntity<DoctorInfoEntity> doctorRegistration(@Valid @RequestBody DoctorDto doctorDto) throws TemplateException, MessagingException, IOException {
        DoctorInfoEntity doctorInfo = this.doctorService.doctorRegistration(doctorDto);
        return new ResponseEntity<DoctorInfoEntity>(doctorInfo, HttpStatus.CREATED);
    }

    @PostMapping(value = "/doctors/{doctorId}/document")
    public ResponseEntity<String> uploadFiles(@PathVariable("doctorId") String doctorId, @RequestParam MultipartFile[] files) throws IOException {
        for(MultipartFile file: files) {
            awss3Service.uploadFile(doctorId, file);
        }
        final String response = "Files(s) uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/doctors/{doctorId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorInfoEntity> approveDoctor(@PathVariable("doctorId") String doctorId, @Valid @RequestBody UpdateDoctorDto updateDoctorDto)  {
        return this.doctorService.doctorUpdate(doctorId, "Active", updateDoctorDto);
    }

    @PutMapping(value = "/doctors/{doctorId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorInfoEntity> rejectDoctor(@PathVariable("doctorId") String doctorId, @Valid @RequestBody UpdateDoctorDto updateDoctorDto) throws IOException {
        return this.doctorService.doctorUpdate(doctorId, "Rejected", updateDoctorDto);
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<List<DoctorInfoEntity>> getDoctorByStatus(@RequestParam(required = false) String status, @RequestParam(required = false) String speciality) throws IOException {
        var obj = this.doctorService.getDoctorByStatus(status, speciality);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "/doctors/{doctorId}")
    public ResponseEntity<DoctorInfoEntity> getDoctorById(@PathVariable String doctorId) throws RecordNotFoundException {
        var obj = this.doctorService.getDoctorById(doctorId);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
