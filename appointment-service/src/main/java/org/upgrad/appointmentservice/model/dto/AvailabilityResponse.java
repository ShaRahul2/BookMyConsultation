package org.upgrad.appointmentservice.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AvailabilityResponse {

    private String DoctorId;
    private Map<Date, List<String>> availabilityMap;

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public Map<Date, List<String>> getAvailabilityMap() {
        return availabilityMap;
    }

    public void setAvailabilityMap(Map<Date, List<String>> availabilityMap) {
        this.availabilityMap = availabilityMap;
    }
}
