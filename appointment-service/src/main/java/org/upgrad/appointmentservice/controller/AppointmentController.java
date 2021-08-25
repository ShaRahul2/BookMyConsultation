package org.upgrad.appointmentservice.controller;

import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.appointmentservice.model.dto.AppointmentDto;
import org.upgrad.appointmentservice.model.dto.AvailabilityDto;
import org.upgrad.appointmentservice.model.dto.AvailabilityResponse;
import org.upgrad.appointmentservice.model.dto.PrescriptionDto;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;
import org.upgrad.appointmentservice.service.appointment.AppointmentService;
import org.upgrad.appointmentservice.service.aws.AWSS3Service;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8084", maxAge = 3600)
@RestController
public class AppointmentController {

    AppointmentService appointmentService;
    AWSS3Service awss3Service;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AWSS3Service awss3Service) {
        this.appointmentService = appointmentService;
        this.awss3Service = awss3Service;
    }

    @SneakyThrows
    @PostMapping("/availability")
    public ResponseEntity<String> setDoctorAvailability(@Valid @RequestBody AvailabilityDto availabilityDto) throws TemplateException, MessagingException, IOException {

        var tt = this.appointmentService.addAvailability(availabilityDto);
        //        AppointmentInfoEntity doctorInfo = this.appointmentService.doctorRegistration(appointmentDto);
        return new ResponseEntity<>(tt, HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{doctorId}/availability")
    public ResponseEntity<AvailabilityResponse> getDoctorsAvailability(@PathVariable("doctorId") String doctorId) throws ParseException {

        var response = this.appointmentService.getAvailability(doctorId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SneakyThrows
    @PostMapping("/appointments")
    public ResponseEntity<String> doctorRegistration(@Valid @RequestBody AppointmentDto appointmentDto) {
        var response = this.appointmentService.bookAppointment(appointmentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentInfoEntity> getAppointment(@PathVariable("appointmentId") String appointmentId) {

        var response = this.appointmentService.getAppointment(appointmentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/appointments")
    public ResponseEntity<List<AppointmentInfoEntity>> getAppointmentsbyUser(@PathVariable("appointmentId") String appointmentId) {

        var response = this.appointmentService.getAppointmentsbyUser(appointmentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SneakyThrows
    @PostMapping("/prescriptions")
    public ResponseEntity<String> getPrescriptions(@RequestBody PrescriptionDto prescriptionDto) {
        this.appointmentService.savePrescription(prescriptionDto);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/updatepayment")
    public ResponseEntity<String> updatePayment(@RequestParam String appointmentId) {
        this.appointmentService.updateAppointment(appointmentId);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
