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

    public DoctorInfoEntity(String firstName, String lastName, String speciality, String dob, String mobile, String emailId, String pan, String status, String approvedBy, String approverComments, Date registrationDate, Date verificationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.dob = dob;
        this.mobile = mobile;
        this.emailId = emailId;
        this.pan = pan;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approverComments = approverComments;
        this.registrationDate = registrationDate;
        this.verificationDate = verificationDate;
    }

    public DBObject createDBObject() {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        //docBuilder.append("_id", this.getId());
        docBuilder.append("firstName", this.getFirstName());
        docBuilder.append("lastName", this.getLastName());
        docBuilder.append("speciality", this.getSpeciality());
        docBuilder.append("dob", this.getDob());
        docBuilder.append("mobile", this.getMobile());
        docBuilder.append("emailId", this.getEmailId());
        docBuilder.append("pan", this.getPan());
        docBuilder.append("status", this.getStatus());
        docBuilder.append("approvedBy", this.getApprovedBy());
        docBuilder.append("approverComments", this.getApproverComments());
        docBuilder.append("registrationDate", this.getRegistrationDate());
        docBuilder.append("verificationDate", this.getVerificationDate());

        return docBuilder.get();
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
