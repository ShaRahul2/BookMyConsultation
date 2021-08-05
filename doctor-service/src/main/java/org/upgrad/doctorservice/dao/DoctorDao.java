package org.upgrad.doctorservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;


public interface DoctorDao extends MongoRepository<DoctorInfoEntity, Integer> {

}