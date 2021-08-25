package org.upgrad.appointmentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;
import org.upgrad.appointmentservice.model.entity.AvailabilityInfoEntity;
import org.upgrad.appointmentservice.model.entity.User;

import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<AppointmentInfoEntity, String> {

    List<AppointmentInfoEntity> findByUserid(String userId);
}
