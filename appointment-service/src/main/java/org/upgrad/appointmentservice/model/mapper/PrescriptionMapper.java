package org.upgrad.appointmentservice.model.mapper;

import org.upgrad.appointmentservice.model.dto.PrescriptionDto;
import org.upgrad.appointmentservice.model.entity.PrescriptionInfoEntity;

import java.text.ParseException;

public class PrescriptionMapper {
    /*
    converting Dto object into Prescription Entity type object
     */
    public static PrescriptionInfoEntity convertDTOToEntity(PrescriptionDto dto) {
        PrescriptionInfoEntity infoEntity = new PrescriptionInfoEntity();
        infoEntity.setDoctorId(dto.getDoctorId());
        infoEntity.setAppointmentId(dto.getAppointmentId());
        infoEntity.setDiagnosis(dto.getDiagnosis());
        infoEntity.setUserId(dto.getUserId());
        infoEntity.setDoctorName(dto.getDoctorName());
        infoEntity.setMedicineList(dto.getMedicineList());
        return infoEntity;
    }
}
