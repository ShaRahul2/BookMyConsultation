package org.upgrad.doctorservice.model.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class UpdateDoctorDto {

    private String status;
    @NotBlank(message= "Please enter Approver Name")
    private String approvedBy;
    @NotBlank(message= "Please add some comments")
    private String approverComments;
    private Date verificationDate;

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

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }
}
