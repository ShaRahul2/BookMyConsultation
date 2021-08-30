package org.upgrad.appointmentservice.model.mapper;

import org.upgrad.appointmentservice.model.dto.PaymentResponse;
import org.upgrad.appointmentservice.model.dto.PrescriptionDto;
import org.upgrad.appointmentservice.model.entity.AppointmentInfoEntity;
import org.upgrad.appointmentservice.model.entity.PrescriptionInfoEntity;

public class PaymentRespMapper {
    /*
    converting entity object into dto type object
     */
    public static PaymentResponse convertEntityToPaymentResp(AppointmentInfoEntity appointmentInfo) {
        PaymentResponse response = new PaymentResponse();
        response.setAppointmentId(appointmentInfo.getAppointmentid());
        response.setCreateDate(appointmentInfo.getCreateddate());

        return response;
    }
}
