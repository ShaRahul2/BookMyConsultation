package org.upgrad.appointmentservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "appointment", schema = "bmc")
public class AppointmentInfoEntity {
    @Id
    @Column(name = "appointment_id", nullable = false, length = 64)
    private String appointmentid;

    @Column(name = "appointment_date", nullable = true)
    private Date appointmentdate;

    @Column(name = "created_date", nullable = true)
    private Date createddate;

    @Column(name = "doctor_id", nullable = true, length = 255)
    private String doctorid;

    @Column(name = "prior_medical_history", nullable = true, length = 255)
    private String priormedicalhistory;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "symtoms", nullable = true, length = 255)
    private String symtoms;

    @Column(name = "time_slots", nullable = true, length = 255)
    private String timeslots;

    @Column(name = "user_id", nullable = true, length = 255)
    private String userid;

    @Column(name = "user_email_id", nullable = true, length = 255)
    private String useremailid;

    @Column(name = "user_name", nullable = true, length = 255)
    private String username;

    @Column(name = "doctor_name", nullable = true, length = 255)
    private String doctorname;

    public AppointmentInfoEntity() {
    }

    public AppointmentInfoEntity(String appointmentid, Date appointmentdate, Date createddate, String doctorid, String priormedicalhistory, String status, String symtoms, String timeslots, String userid, String useremailid, String username, String doctorname) {
        this.appointmentid = appointmentid;
        this.appointmentdate = appointmentdate;
        this.createddate = createddate;
        this.doctorid = doctorid;
        this.priormedicalhistory = priormedicalhistory;
        this.status = status;
        this.symtoms = symtoms;
        this.timeslots = timeslots;
        this.userid = userid;
        this.useremailid = useremailid;
        this.username = username;
        this.doctorname = doctorname;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public Date getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(Date appointmentdate) {
        this.appointmentdate = appointmentdate;
    }
    @JsonIgnore
    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    @JsonIgnore
    public String getPriormedicalhistory() {
        return priormedicalhistory;
    }

    public void setPriormedicalhistory(String priormedicalhistory) {
        this.priormedicalhistory = priormedicalhistory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonIgnore
    public String getSymtoms() {
        return symtoms;
    }

    public void setSymtoms(String symtoms) {
        this.symtoms = symtoms;
    }

    public String getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(String timeslots) {
        this.timeslots = timeslots;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    @JsonIgnore
    public String getUseremailid() {
        return useremailid;
    }

    public void setUseremailid(String useremailid) {
        this.useremailid = useremailid;
    }
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonIgnore
    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    @Override
    public String toString() {
        return "AppointmentInfoEntity{" +
                "appointmentid='" + appointmentid + '\'' +
                ", appointmentdate=" + appointmentdate +
                ", createddate=" + createddate +
                ", doctorid='" + doctorid + '\'' +
                ", priormedicalhistory='" + priormedicalhistory + '\'' +
                ", status='" + status + '\'' +
                ", symtoms='" + symtoms + '\'' +
                ", timeslots='" + timeslots + '\'' +
                ", userid='" + userid + '\'' +
                ", useremailid='" + useremailid + '\'' +
                ", username='" + username + '\'' +
                ", doctorname='" + doctorname + '\'' +
                '}';
    }
}
