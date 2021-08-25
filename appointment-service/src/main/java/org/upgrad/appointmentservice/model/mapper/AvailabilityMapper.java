package org.upgrad.appointmentservice.model.mapper;

import org.upgrad.appointmentservice.model.dto.AvailabilityDto;
import org.upgrad.appointmentservice.model.entity.AvailabilityInfoEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AvailabilityMapper {

    public static AvailabilityInfoEntity convertDTOToEntity(AvailabilityDto dto) throws ParseException {
        AvailabilityInfoEntity availability = new AvailabilityInfoEntity();
        availability.setDoctorid(dto.getDoctorid());
        availability.setAvailabilitydate(dto.getAvailability_date());
        availability.setTime_slot(dto.getTime_slot());
        availability.setIs_booked(dto.getIs_booked());
        return availability;
    }
}
