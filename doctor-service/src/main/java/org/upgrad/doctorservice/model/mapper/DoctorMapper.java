package org.upgrad.doctorservice.model.mapper;


import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

public class DoctorMapper {

    public static DoctorInfoEntity convertDTOToEntity(DoctorDto doctorDto) {
        DoctorInfoEntity doctorInfo = new DoctorInfoEntity();
        doctorInfo.setFirstName(doctorDto.getFirstName());
        doctorInfo.setLastName(doctorDto.getLastName());
        if (doctorDto.getSpeciality() != null) {
            doctorInfo.setSpeciality(doctorDto.getSpeciality());
        }
        doctorInfo.setSpeciality("General Physician");
        doctorInfo.setDob(doctorDto.getDob());
        doctorInfo.setMobile(doctorDto.getMobile());
        doctorInfo.setEmailId(doctorDto.getEmailId());
        doctorInfo.setPan(doctorDto.getPan());
        doctorInfo.setStatus(doctorDto.getStatus());
        doctorInfo.setApprovedBy(doctorDto.getApprovedBy());
        doctorInfo.setApproverComments(doctorDto.getApproverComments());
        doctorInfo.setRegistrationDate(doctorDto.getRegistrationDate());
        doctorInfo.setVerificationDate(doctorDto.getVerificationDate());
        return doctorInfo;
    }
}
