package org.upgrad.doctorservice.service.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.doctorservice.dao.DoctorDao;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;
import org.upgrad.doctorservice.model.mapper.DoctorMapper;

@Service
public class DoctorService {

    DoctorDao doctorDao;
    RestTemplate restTemplate;

    @Autowired
    public DoctorService(DoctorDao doctorDao, RestTemplate restTemplate) {
        this.doctorDao = doctorDao;
        this.restTemplate = restTemplate;
    }

    public DoctorInfoEntity doctorRegistration(DoctorDto doctorRequest) {
        var doctorInfo = DoctorMapper.convertDTOToEntity(doctorRequest);
        doctorDao.save(doctorInfo);
        return null;
    }
}
