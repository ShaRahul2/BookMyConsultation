package org.upgrad.appointmentservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.appointmentservice.model.entity.PrescriptionInfoEntity;

public interface PrescriptionDao extends MongoRepository<PrescriptionInfoEntity, String> {

}