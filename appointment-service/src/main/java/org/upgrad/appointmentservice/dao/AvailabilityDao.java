package org.upgrad.appointmentservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upgrad.appointmentservice.model.entity.AvailabilityInfoEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface AvailabilityDao extends JpaRepository<AvailabilityInfoEntity, String> {

    List<AvailabilityInfoEntity> findByDoctorid(String DoctorId);

    @Query("Select a from AvailabilityInfoEntity a where a.doctorid = ?1 group by a.availabilitydate")
    List<AvailabilityInfoEntity> findDistinctByAvailabilitydate(String DoctorId);

    @Query("Select a.timeslot from AvailabilityInfoEntity a where a.availabilitydate = ?1")
    List<String> findByAvailabilitydate(Date availabilityDate);

    @Query("Select a from AvailabilityInfoEntity a where a.doctorid = :doctorId and a.availabilitydate = DATE(:availabilitydate)  and a.timeslot = :timeSlot and a.is_booked = 0")
    AvailabilityInfoEntity findByDoctoridAndAvailabilitydateAndTimeslot(String doctorId, String availabilitydate, String timeSlot);

}
