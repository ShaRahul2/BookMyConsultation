package org.upgrad.doctorservice.model.entity;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.upgrad.doctorservice.CustomValidator.CustomEmailValidator;

import javax.validation.constraints.*;
import java.util.Date;

@Document(collection = "doctorService")
public class DoctorInfoEntity {
    @NotBlank
    @Size(min=2, max=10)
    private String firstName;

    @NotBlank
    @Size(min=2, max=10)
    private String lastName;

    private String speciality;

    @NotBlank(message = "Please enter your Date of birth.")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Past(message = "That's impossible.")
    private String dob;

    @NotNull
    @Pattern(regexp="(^[0-9]{10})", message = "Please enter a valid mobile no")
    private String mobile;
    @NotNull
    @CustomEmailValidator
    private String emailId;

    @NotNull
    @Size(min=10, max=10 )
    @Pattern(regexp = "([A-Z]{5}[0-9]{4}[A-Z]{1})", message = "Please enter valid pan card number")
    private String pan;
    private String status;
    private String approvedBy;
    private String approverComments;
    private Date registrationDate;
    private Date verificationDate;

    public DoctorInfoEntity(){

    }

    @Override
    public String toString() {
        return "DoctorInfoEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", dob='" + dob + '\'' +
                ", mobile='" + mobile + '\'' +
                ", emailId='" + emailId + '\'' +
                ", pan='" + pan + '\'' +
                ", status='" + status + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", approverComments='" + approverComments + '\'' +
                ", registrationDate=" + registrationDate +
                ", verificationDate=" + verificationDate +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments(String approverComments) {
        this.approverComments = approverComments;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }
}
