package org.upgrad.appointmentservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "availability", schema = "bmc")
public class AvailabilityInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "availability_date", nullable = true, length = 255)
    private Date availabilitydate;

    @Column(name = "doctor_id", nullable = true, length = 255)
    private String doctorid;

    @Column(name = "is_booked", nullable = false, length = 1)
    private Integer is_booked;

    @Column(name = "time_slot", nullable = true, length = 255)
    private String timeslot;

    public AvailabilityInfoEntity() {
    }

    public AvailabilityInfoEntity(Integer id, Date availability_date, String doctor_id, Integer is_booked, String time_slot) {
        this.id = id;
        this.availabilitydate = availability_date;
        this.doctorid = doctor_id;
        this.is_booked = is_booked;
        this.timeslot = time_slot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAvailabilitydate() {
        return availabilitydate;
    }

    public void setAvailabilitydate(Date availability_date) {
        this.availabilitydate = availability_date;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctor_id) {
        this.doctorid = doctor_id;
    }

    public Integer getIs_booked() {
        return is_booked;
    }

    public void setIs_booked(Integer is_booked) {
        this.is_booked = is_booked;
    }

    public String getTime_slot() {
        return timeslot;
    }

    public void setTime_slot(String time_slot) {
        this.timeslot = time_slot;
    }

    @Override
    public String toString() {
        return "AvailabilityInfoEntity{" +
                "id=" + id +
                ", availability_date=" + availabilitydate +
                ", doctor_id='" + doctorid + '\'' +
                ", is_booked=" + is_booked +
                ", time_slot='" + timeslot + '\'' +
                '}';
    }
}
