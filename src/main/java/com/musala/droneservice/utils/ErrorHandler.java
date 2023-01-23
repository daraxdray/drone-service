package com.musala.droneservice.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * HELPS HANDLE WRONG INPUT ERROR WITH PROPERTIES
 * --------message---: A simple message showing the "Wrong Input"
 * --------error----: An Object containing the field with wrong input and the error message
 * --------status---: This is the response message derived from HTTPSTATUSCODE
 */

@ControllerAdvice
class ErrorHandler  extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        if(ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException iex = (InvalidFormatException) ex.getCause();
            List<Map<String,String>> errors = new ArrayList<>();
            Map<String,Object> err = new HashMap<>();
            err.put("message","Invalid Input");
            err.put("status",status);

            for (JsonMappingException.Reference reference : iex.getPath()) {
                Map<String,String> error = new HashMap<>();
                error.put("field", reference.getFieldName());
                error.put("message", iex.getOriginalMessage());
                err.put("error",error);
            }
            return handleExceptionInternal(ex, err, new HttpHeaders(), status, request);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }
}
