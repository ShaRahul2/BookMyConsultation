package org.upgrad.doctorservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.service.doctor.DoctorService;

@RestController
public class DoctorController {

    DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorInfoEntity> doctorRegistration(@RequestBody DoctorDto doctorDto) throws Exception {
        DoctorInfoEntity bookingInfo = this.doctorService.doctorRegistration(doctorDto);
        return new ResponseEntity<DoctorInfoEntity>(bookingInfo, HttpStatus.CREATED);
    }
}
