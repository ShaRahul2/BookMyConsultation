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
     * Saves customer personal details.
     * @param doctorDto
     * @return
     */
    DoctorInfoEntity doctorRegistration(DoctorDto doctorDto) throws TemplateException, IOException, MessagingException;

    ResponseEntity<DoctorInfoEntity> doctorUpdate(String doctorId, String Status, UpdateDoctorDto updateDoctorDto);

    List<DoctorInfoEntity> getDoctorByStatus(String status, String speciality);

    DoctorInfoEntity getDoctorById(String doctorId) throws RecordNotFoundException;
}
