package org.upgrad.paymentservice.model.dto;

import java.util.List;

public class CustomResponse {
    private String message;
    private int statusCode;
    private List<String> errorDetails;

    public CustomResponse(String message, int statusCode, List<String> errorDetails) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }


    public List<String> getErrorDetails() {
        return errorDetails;
    }
}

