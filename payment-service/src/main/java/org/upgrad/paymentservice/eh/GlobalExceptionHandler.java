package org.upgrad.paymentservice.eh;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.upgrad.paymentservice.exceptions.CustomException;
import org.upgrad.paymentservice.model.dto.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomResponse> handleCustomException(Exception ex) {
        CustomResponse exceptionResponse = new CustomResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<CustomResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
