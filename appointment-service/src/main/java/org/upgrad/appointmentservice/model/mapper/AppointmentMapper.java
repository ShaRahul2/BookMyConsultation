package org.upgrad.appointmentservice.model.mapper;

import org.upgrad.appointmentservice.model.dto.AppointmentDto;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;

import java.text.ParseException;

public class AppointmentMapper {

    public static AppointmentInfoEntity convertDTOToEntity(AppointmentDto dto) throws ParseException {
        AppointmentInfoEntity infoEntity = new AppointmentInfoEntity();
        infoEntity.setAppointmentdate(dto.getAppointmentDate());
        infoEntity.setDoctorid(dto.getDoctorId());
        infoEntity.setTimeslots(dto.getTimeSlot());
        infoEntity.setUserid(dto.getUserId());
        return infoEntity;
    }
}
