package org.upgrad.appointmentservice.service.appointment;

import org.upgrad.appointmentservice.model.dto.*;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {

    String addAvailability(AvailabilityDto availabilityDto, String doctorId)  throws ParseException;

    AvailabilityResponse getAvailability(String doctorId) throws ParseException;

    String bookAppointment(AppointmentDto appointmentDto) throws ParseException;

    AppointmentInfoEntity getAppointment(String appointmentId);

    List<AppointmentInfoEntity> getAppointmentsbyUser(String userId);

    void savePrescription(PrescriptionDto prescriptionDto) throws ParseException;

    PaymentResponse updateAppointment(String appointmentId);
}
