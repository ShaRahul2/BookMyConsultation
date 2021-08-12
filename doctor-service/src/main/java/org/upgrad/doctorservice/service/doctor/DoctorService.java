package org.upgrad.doctorservice.service.doctor;

import freemarker.template.TemplateException;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

import javax.mail.MessagingException;
import java.io.IOException;

public interface DoctorService {


    /**
     * Saves customer personal details.
     * @param doctorDto
     * @return
     */
    public DoctorInfoEntity doctorRegistration(DoctorDto doctorDto) throws TemplateException, IOException, MessagingException;
}
