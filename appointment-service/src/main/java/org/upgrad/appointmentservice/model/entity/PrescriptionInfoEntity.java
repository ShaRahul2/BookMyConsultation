package org.upgrad.appointmentservice.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "prescription")
public class PrescriptionInfoEntity {

    private String id;
    private String userId;
    private String doctorId;
    private String doctorName;
    private String appointmentId;
    private String diagnosis;
    private List<Map<String, String>> medicineList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Map<String, String>> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Map<String, String>> medicineList) {
        this.medicineList = medicineList;
    }
}
