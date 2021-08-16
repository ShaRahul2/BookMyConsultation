package org.upgrad.doctorservice.service.doctor.impl;

import freemarker.template.TemplateException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.doctorservice.dao.DoctorDao;
import org.upgrad.doctorservice.exceptions.CustomException;
import org.upgrad.doctorservice.exceptions.RecordNotFoundException;
import org.upgrad.doctorservice.exceptions.handler.CustomExceptionHandler;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.dto.UpdateDoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.model.mapper.DoctorMapper;
import org.upgrad.doctorservice.service.doctor.DoctorService;
import org.upgrad.doctorservice.service.ses.SesEmailVerificationService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (doctorInfo.getSpeciality() == null) {
            doctorInfo.setSpeciality("General Physician");
        }
        if(doctorInfo.getStatus()== null){
            doctorInfo.setStatus("Pending");
        }
        if(doctorInfo.getRegistrationDate() == null){
            doctorInfo.setRegistrationDate(DateTime.now().toDate());
        }
        var sb = doctorDao.save(doctorInfo);
        //verifyEmail.verifyEmail(sb.getEmailId());
        verifyEmail.sendEmail(doctorRequest);
        return sb;
    }

    public ResponseEntity<DoctorInfoEntity> doctorUpdate(String doctorId, String Status, UpdateDoctorDto updateDoctorDto) {
        var doctorInfo = doctorDao.findById(doctorId);
        if (doctorInfo.isPresent()) {
            DoctorInfoEntity _doctorInfo = doctorInfo.get();
            _doctorInfo.setApprovedBy(updateDoctorDto.getApprovedBy());
            _doctorInfo.setApproverComments(updateDoctorDto.getApproverComments());
            _doctorInfo.setStatus(Status);
            _doctorInfo.setVerificationDate(DateTime.now().toDate());
            return new ResponseEntity<>(doctorDao.save(_doctorInfo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<DoctorInfoEntity> getDoctorByStatus(String status, String speciality) {
        List<DoctorInfoEntity> obj = new ArrayList<DoctorInfoEntity>();
        if (speciality == null && status != null) {
             obj = doctorDao.findByStatus(status).stream().limit(20).collect(Collectors.toList());
        } else if (speciality != null && status == null) {
             obj = doctorDao.findBySpeciality(speciality).stream().limit(20).collect(Collectors.toList());
        } else {
             obj = doctorDao.findByStatusAndSpeciality(status, speciality).stream().limit(20).collect(Collectors.toList());
        }
        return obj;
    }

    public DoctorInfoEntity getDoctorById(String doctorId) throws RecordNotFoundException {
        var obj = doctorDao.findById(doctorId);
        return obj.orElseThrow(() -> new RecordNotFoundException(""));
    }
}