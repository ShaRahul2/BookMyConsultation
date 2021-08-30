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

    /**
     * This endpoint is responsible for collecting information about the doctor.
     *     URI: /doctors
     *     HTTP method: POST
     *     Request body: firstName, lastName, dob, emailID, mobile, and PAN.
     *     Role: USER, ADMIN. If the token has the role of either USER or ADMIN, then the endpoint can be accessed.
     *     also implemeted the validation check, If any of the above-mentioned validation fails, the API will return the HTTP status code 400 BadRequest.
     * @param doctorDto
     * @return
     */
    @SneakyThrows
    @PostMapping("/doctors")
    public ResponseEntity<DoctorInfoEntity> doctorRegistration(@Valid @RequestBody DoctorDto doctorDto){
        DoctorInfoEntity doctorInfo = this.doctorService.doctorRegistration(doctorDto);
        return new ResponseEntity<DoctorInfoEntity>(doctorInfo, HttpStatus.CREATED);
    }

    /**
     * This endpoint is responsible for uploading the documents to an S3 bucket by the doctor.
     *     URI: /doctors/{doctorId}/document
     *     HTTP method: POST
     *     Request body: formData needs to be selected, and the file that needs to be uploaded should be provided.
     * @param doctorId
     * @param files
     * @return
     */
    @PostMapping(value = "/doctors/{doctorId}/document")
    public ResponseEntity<String> uploadFiles(@PathVariable("doctorId") String doctorId, @RequestParam MultipartFile[] files) {
        for(MultipartFile file: files) {
            awss3Service.uploadFile(doctorId, file);
        }
        final String response = "Files(s) uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This endpoint is responsible for approving the doctor’s registration request.
     *     URI: /doctors/{doctorId}/approve
     *     HTTP method: PUT
     *     Request body: approvedBy, approverComments
     *     Role: ADMIN. If the token has the role of ADMIN, then only this endpoint should be accessed. This means that only ADMIN can approve the registration of the doctor.
     *     The doctor object with the new fields approvedBy and approverComments is sent to the same Kafka topic.
     * Multiple exception may occur at runtime so to handle them implemented the exception type
     * @param doctorId: doctor need to passed as path variable, to approve the doctor
     * @param updateDoctorDto: doctor Apporver name and proper comment/ reason is mentioned in this
     * @return
     * @throws TemplateException
     * @throws MessagingException
     * @throws IOException
     */
    @PutMapping(value = "/doctors/{doctorId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorInfoEntity> approveDoctor(@PathVariable("doctorId") String doctorId, @Valid @RequestBody UpdateDoctorDto updateDoctorDto) throws TemplateException, MessagingException, IOException {
        return this.doctorService.doctorUpdate(doctorId, "Active", updateDoctorDto);
    }

    /**
     *  This endpoint is responsible for rejecting the registration of the doctor and then send email
     *  needs to be sent to the email ID provided by the doctor stating that their registration has been rejected.
     *  The email needs to be sent by the
     *  notification service, the details of which will be covered in the upcoming segments..
     *  URI: /doctors/{doctorId}/reject
     *  HTTP method: PUT
     * Request body: approvedBy, approverComments
     * @param doctorId: doctor need to passed as path variable, to reject the doctor
     * @param updateDoctorDto: doctor rejecter name and proper comment/ reason is mentioned in this
     * @return
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    @PutMapping(value = "/doctors/{doctorId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorInfoEntity> rejectDoctor(@PathVariable("doctorId") String doctorId, @Valid @RequestBody UpdateDoctorDto updateDoctorDto) throws IOException, TemplateException, MessagingException {
        return this.doctorService.doctorUpdate(doctorId, "Rejected", updateDoctorDto);
    }

    /**
     * This endpoint is responsible for returning the list of 20 doctors sorted by ratings.
     * this is conditional based filter endpoint which will get the resultset on the basis of request parameters.
     * if a request is sent to the following URI: /doctors?status=Pending&&speciality=Dentist,
     *     this should return the list of doctors pending approval and with the speciality as Dentist.
     * @param status
     * @param speciality
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/doctors")
    public ResponseEntity<List<DoctorInfoEntity>> getDoctorByStatus(@RequestParam(required = false) String status, @RequestParam(required = false) String speciality) throws IOException {
        var obj = this.doctorService.getDoctorByStatus(status, speciality);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    /**
     *  This endpoint is responsible for returning the details of the doctor based on the doctor ID and
     * @param doctorId
     * @return DoctorInfoEntity:  is no exception occured
     * @throws RecordNotFoundException: if the doctor id is invalid the following error message
     */
    @GetMapping(value = "/doctors/{doctorId}")
    public ResponseEntity<DoctorInfoEntity> getDoctorById(@PathVariable String doctorId) throws RecordNotFoundException {
        var obj = this.doctorService.getDoctorById(doctorId);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
