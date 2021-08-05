package org.upgrad.doctorservice.service.doctor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.doctorservice.dao.DoctorDao;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.model.mapper.DoctorMapper;
import org.upgrad.doctorservice.service.doctor.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    DoctorDao doctorDao;
    RestTemplate restTemplate;

    @Autowired
    public DoctorServiceImpl(DoctorDao doctorDao, RestTemplate restTemplate) {
        this.doctorDao = doctorDao;
        this.restTemplate = restTemplate;
    }

    public DoctorInfoEntity doctorRegistration(DoctorDto doctorRequest) {
        var doctorInfo = DoctorMapper.convertDTOToEntity(doctorRequest);
        doctorDao.save(doctorInfo);
        return null;
    }
}