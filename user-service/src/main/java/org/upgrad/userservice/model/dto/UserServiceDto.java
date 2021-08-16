package org.upgrad.userservice.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.upgrad.userservice.CustomValidator.CustomEmailValidator;
import java.util.Date;

public class UserServiceDto {
    @NotBlank(message = "Please enter valid first name")
    @Size(min = 2, max = 10)
    private String firstName;

    @NotBlank(message = "Please enter valid last name")
    @Size(min = 2, max = 10)
    private String lastName;

    @NotBlank(message = "Please enter your Date of birth.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String dob;

    @NotBlank
    @Pattern(regexp = "(^[0-9]{10})", message = "Please enter a valid mobile no")
    private String mobile;

    @CustomEmailValidator
    private String emailId;


    private Date createdDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}