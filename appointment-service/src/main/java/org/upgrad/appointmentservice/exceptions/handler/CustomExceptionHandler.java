package org.upgrad.appointmentservice.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.upgrad.appointmentservice.exceptions.RecordNotFoundException;
import org.upgrad.appointmentservice.model.dto.CustomResponse;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private String NO_RECORDS_FOUND = "NO_RECORDS_FOUND";
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e, WebRequest req) {

//        List<String> errorDetails = new ArrayList<String>();
//        errorDetails.add(e.getLocalizedMessage());
//        CustomResponse response = new CustomResponse(NO_RECORDS_FOUND, HttpStatus.BAD_REQUEST.value(), errorDetails);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", "ERR_RESOURCE_NOT_FOUND");
        body.put("errorMessage", "Requested resource is not avaiable");
        body.put("errorFields", null);
        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", "ERR_INVALID_INPUT");
        body.put("errorMessage", "Invalid input. Parameter name: ");
        List<String> errors = ex.getBindingResult().getFieldErrors().
                stream().map(x -> x.getField().toUpperCase(Locale.ROOT)).collect(Collectors.toList());

        body.put("errorFields", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

