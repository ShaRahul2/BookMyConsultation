package org.upgrad.appointmentservice.controller;

import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.appointmentservice.model.dto.*;
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

    /**
     *  Constructor layer to create instance of service layers
     * @param appointmentService
     * @param awss3Service
     */
    @Autowired
    public AppointmentController(AppointmentService appointmentService, AWSS3Service awss3Service) {
        this.appointmentService = appointmentService;
        this.awss3Service = awss3Service;
    }

    /**
     *  This endpoint is responsible for updating the availability of the doctors.
     *     URI: /doctor/{doctorId}/availability
     *     HTTP method: POST
     * @param availabilityDto : Availability Map
     * @param doctorId
     * @return
     */
    @SneakyThrows
    @PostMapping("/doctor/{doctorId}/availability")
    public ResponseEntity<String> setDoctorAvailability(@Valid @RequestBody AvailabilityDto availabilityDto, @PathVariable("doctorId") String doctorId) {

        var tt = this.appointmentService.addAvailability(availabilityDto, doctorId);
        //        AppointmentInfoEntity doctorInfo = this.appointmentService.doctorRegistration(appointmentDto);
        return new ResponseEntity<>(tt, HttpStatus.CREATED);
    }

    /**
     *     This endpoint is responsible for returning the availability of the doctors.
     *     URI: /doctor/{doctorId}/availability
     *     HTTP method: GET
     *     Role: USER, ADMIN
     * @param doctorId
     * @return AvailabilityResponse
     * @throws ParseException: while parsing the date this may occur
     */
    /*

     */
    @GetMapping("/doctor/{doctorId}/availability")
    public ResponseEntity<AvailabilityResponse> getDoctorsAvailability(@PathVariable("doctorId") String doctorId) throws ParseException {

        var response = this.appointmentService.getAvailability(doctorId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     *  This endpoint is responsible for booking an appointment.
     *     URI: /appointments
     *     HTTP method: POST
     *     Request body:
     *     Role: USER, ADMIN
     * @param appointmentDto: doctorId, userId, appointmentDate, timeSlot; this contains the data to book the appointment
     * @return String
     */
    @SneakyThrows
    @PostMapping("/appointments")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        var response = this.appointmentService.bookAppointment(appointmentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * This endpoint is responsible for retrieving the details of an appointment.
     *     URI: /appointments/{appointmentId}
     *     HTTP method: GET
     *     Role: USER, ADMIN
     * @param appointmentId: to fetch assoiciated appointment details for the appointment id
     * @return AppointmentInfoEntity : this contains the entity info the request appointment id
     */
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentInfoEntity> getAppointment(@PathVariable("appointmentId") String appointmentId) {

        var response = this.appointmentService.getAppointment(appointmentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * This endpoint is responsible for retrieving the details of all the appointments corresponding to a userId.
     *     URI: /users/{userId}/appointments
     *     HTTP method: GET
     *     Role: USER, ADMIN
     * @param userId
     * @return AppointmentInfoEntity: list type of data will be return of the user
     */
    @GetMapping("/users/{userId}/appointments")
    public ResponseEntity<List<AppointmentInfoEntity>> getAppointmentsbyUser(@PathVariable("userId") String userId) {

        var response = this.appointmentService.getAppointmentsbyUser(userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     *    To save the prescription in Mongo Db
     *        The prescription can be sent only if the status of the appointment is Confirmed
     *        if the status of the appointment is set to ‘PendingPayment’ then returning with appropriate response message
     *
     *        URI: /prescriptions
     *        HTTP method: POST
     *        Request body: appointmentId, doctorId, userId, diagnosis, medicineList
     * @param prescriptionDto
     * @return is empty
     */
    @SneakyThrows
    @PostMapping("/prescriptions")
    public ResponseEntity<String> getPrescriptions(@RequestBody PrescriptionDto prescriptionDto) {
        this.appointmentService.savePrescription(prescriptionDto);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * this endpoint is exposed for the payment service to change the appointment status
     *  TO UPDATE THE STATUS FOR APPOINTMENT ID  in DB
     *     Change Status from PendingPayement to Confirmed
     * @param appointmentId
     * @return PaymentResponse : contains id, appointmentid, createDate
     */
    @CrossOrigin
    @PostMapping("/updatepayment")
    public ResponseEntity<PaymentResponse> updatePayment(@RequestParam(required = false) String appointmentId) {
        var obj = this.appointmentService.updateAppointment(appointmentId);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }
}
