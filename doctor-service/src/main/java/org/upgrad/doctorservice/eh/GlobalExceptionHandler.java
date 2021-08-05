package org.upgrad.doctorservice.eh;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.upgrad.doctorservice.exceptions.CustomException;
import org.upgrad.doctorservice.model.dto.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomResponse> handleCustomException(Exception ex) {
        CustomResponse exceptionResponse = new CustomResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<CustomResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
