package org.upgrad.doctorservice.model.mapper;


import org.joda.time.DateTime;
import org.upgrad.doctorservice.model.dto.DoctorDto;
import org.upgrad.doctorservice.model.entity.DoctorInfoEntity;

public class DoctorMapper {

    public static DoctorInfoEntity convertDTOToEntity(DoctorDto doctorDto) {
        DoctorInfoEntity doctorInfo = new DoctorInfoEntity();
        doctorInfo.setFirstName(doctorDto.getFirstName());
        doctorInfo.setLastName(doctorDto.getLastName());
        doctorInfo.setSpeciality(doctorDto.getSpeciality());
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

    public static DoctorDto convertEntityToDTO(DoctorInfoEntity doctorInfo) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setFirstName(doctorInfo.getFirstName());
        doctorDto.setLastName(doctorInfo.getLastName());
        doctorDto.setSpeciality(doctorInfo.getSpeciality());
        doctorDto.setDob(doctorInfo.getDob());
        doctorDto.setMobile(doctorInfo.getMobile());
        doctorDto.setEmailId(doctorInfo.getEmailId());
        doctorDto.setPan(doctorInfo.getPan());
        doctorDto.setStatus(doctorInfo.getStatus());
        doctorDto.setApprovedBy(doctorInfo.getApprovedBy());
        doctorDto.setApproverComments(doctorInfo.getApproverComments());
        doctorDto.setRegistrationDate(doctorInfo.getRegistrationDate());
        doctorDto.setVerificationDate(doctorInfo.getVerificationDate());
        return doctorDto;
    }
}
