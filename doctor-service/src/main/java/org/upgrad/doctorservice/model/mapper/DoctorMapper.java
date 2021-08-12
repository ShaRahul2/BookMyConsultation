package org.upgrad.doctorservice.model.mapper;


import org.joda.time.DateTime;
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
        else {
            doctorInfo.setSpeciality("General Physician");
        }
        doctorInfo.setDob(doctorDto.getDob());
        doctorInfo.setMobile(doctorDto.getMobile());
        doctorInfo.setEmailId(doctorDto.getEmailId());
        doctorInfo.setPan(doctorDto.getPan());
        if(doctorDto.getStatus()!= null){
            doctorInfo.setStatus(doctorDto.getStatus());
        }else {
            doctorInfo.setStatus("Pending");
        }
        doctorInfo.setApprovedBy(doctorDto.getApprovedBy());
        doctorInfo.setApproverComments(doctorDto.getApproverComments());
        if(doctorDto.getRegistrationDate() != null){
            doctorInfo.setRegistrationDate(doctorDto.getRegistrationDate());
        }
        else {
            doctorInfo.setRegistrationDate(DateTime.now().toDate());
        }
        doctorInfo.setVerificationDate(doctorDto.getVerificationDate());
        return doctorInfo;
    }
}
