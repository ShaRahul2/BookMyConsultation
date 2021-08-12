package org.upgrad.doctorservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

import java.util.List;


public interface DoctorDao extends MongoRepository<DoctorInfoEntity, String> {
    List<DoctorInfoEntity> findByStatus(String status);

    List<DoctorInfoEntity> findBySpeciality(String speciality);

    List<DoctorInfoEntity> findByStatusAndSpeciality(String status, String speciality);
}