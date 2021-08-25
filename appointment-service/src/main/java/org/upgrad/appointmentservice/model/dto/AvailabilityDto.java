package org.upgrad.appointmentservice.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AvailabilityDto {

    private Integer id;
    private Date availability_date;
    private String doctorid;
    private Integer is_booked;
    private String time_slot;
    private Map<String, List<String>> availabilityMap;

    public Map<String, List<String>> getAvailabilityMap() {
        return availabilityMap;
    }

    public void setAvailabilityMap(Map<String, List<String>> availabilityMap) {
        this.availabilityMap = availabilityMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAvailability_date() {
        return availability_date;
    }

    public void setAvailability_date(Date availability_date) {
        this.availability_date = availability_date;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public Integer getIs_booked() {
        return is_booked;
    }

    public void setIs_booked(Integer is_booked) {
        this.is_booked = is_booked;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }
}