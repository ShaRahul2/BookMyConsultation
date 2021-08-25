package org.upgrad.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.upgrad.paymentservice.model.dto.PaymentResponse;

import java.io.IOException;
import java.util.List;

@RestController
public class PaymentController {

    RestTemplate restTemplate;

    @Autowired
    public PaymentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("http://localhost:8083/")
    private String paymentserviceUrl;

    @PostMapping(value = "/payments")
    public ResponseEntity<PaymentResponse> updatePayment(@RequestParam(required = false) String appointmentId) {

        String url = this.paymentserviceUrl + "/updatepayment/" + appointmentId;
        var doc = restTemplate.getForEntity(url, Object.class);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
