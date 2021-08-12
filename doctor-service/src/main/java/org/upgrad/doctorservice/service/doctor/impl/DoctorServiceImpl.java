package org.upgrad.doctorservice.service.doctor.impl;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.doctorservice.dao.DoctorDao;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.model.mapper.DoctorMapper;
import org.upgrad.doctorservice.service.doctor.DoctorService;
import org.upgrad.doctorservice.service.ses.SesEmailVerificationService;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class DoctorServiceImpl implements DoctorService {

    DoctorDao doctorDao;
    RestTemplate restTemplate;
    SesEmailVerificationService verifyEmail;
    @Autowired
    public DoctorServiceImpl(DoctorDao doctorDao, RestTemplate restTemplate, SesEmailVerificationService verifyEmail) {
        this.doctorDao = doctorDao;
        this.restTemplate = restTemplate;
        this.verifyEmail = verifyEmail;
    }

    public DoctorInfoEntity doctorRegistration(DoctorDto doctorRequest) throws TemplateException, IOException, MessagingException {
        var doctorInfo = DoctorMapper.convertDTOToEntity(doctorRequest);
        var sb = doctorDao.save(doctorInfo);
        verifyEmail.verifyEmail(sb.getEmailId());
        verifyEmail.sendEmail(doctorRequest);
        return sb;
    }
}