package org.upgrad.doctorservice.service.doctor;

import freemarker.template.TemplateException;
import org.springframework.http.ResponseEntity;
import org.upgrad.doctorservice.exceptions.RecordNotFoundException;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.dto.UpdateDoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DoctorService {


    /**
     * Saves doctordetails.
     * @param doctorDto
     * @return
     */
    DoctorInfoEntity doctorRegistration(DoctorDto doctorDto);

    /**
     * updating  the doctor status
     * @param doctorId
     * @param Status
     * @param updateDoctorDto
     * @return
     * @throws TemplateException
     * @throws MessagingException
     * @throws IOException
     */
    ResponseEntity<DoctorInfoEntity> doctorUpdate(String doctorId, String Status, UpdateDoctorDto updateDoctorDto) throws TemplateException, MessagingException, IOException;

    /**
     * fetch the resultset and filter the set basis on status and speciality
     * @param status : active/ pending
     * @param speciality: general-physician
     * @return
     */
    List<DoctorInfoEntity> getDoctorByStatus(String status, String speciality);

    DoctorInfoEntity getDoctorById(String doctorId) throws RecordNotFoundException;
}
