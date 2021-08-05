package org.upgrad.doctorservice.service.doctor;

import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

public interface DoctorService {


    /**
     * Saves customer personal details.
     * @param doctorDto
     * @return
     */
    public DoctorInfoEntity doctorRegistration(DoctorDto doctorDto);
}
