package org.upgrad.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.upgrad.paymentservice.model.dto.AppointmentDto;
import org.upgrad.paymentservice.model.dto.PaymentResponse;

@RestController
public class PaymentController {

    RestTemplate restTemplate;

    @Autowired
    public PaymentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${url.service.appointment}")
    private String paymentserviceUrl;

    /**
     * This endpoint is responsible for making payments.
     *     URI: /payments?appointmentId=<the appointmentId for which you want to make a payment>
     *     HTTP method: POST
     *     Request body: Nothing needs to be sent as part of the request body.
     * @param appointmentId: the appointmentId for which you want to make a payment
     * @return PaymentResponse
     */
    @PostMapping(value = "/payments")
    public ResponseEntity<PaymentResponse> updatePayment(@RequestParam(required = false) String appointmentId) {

        var appdto = new AppointmentDto();
        String url = this.paymentserviceUrl + "/updatepayment?appointmentId=" + appointmentId;
        var doc = restTemplate.postForObject(url, appdto , PaymentResponse.class);

        return new ResponseEntity<>(doc, HttpStatus.CREATED);
    }
}
