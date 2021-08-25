package org.upgrad.appointmentservice.exceptions;

public class PendingPaymentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PendingPaymentException(String message) {
        super(message);
    }
}
