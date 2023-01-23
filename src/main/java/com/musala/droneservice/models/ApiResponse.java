package com.musala.droneservice.models;

import org.springframework.http.HttpStatus;

public class ApiResponse<T>
{
    public ApiResponse(String message, HttpStatus statusCode) {

        this.message = message;
        this.status = statusCode;
    }

    public ApiResponse(String message, HttpStatus statusCode, T data) {
        this.message = message;
        this.status = statusCode;
        this.data = data;
    }

    private String message;
    private HttpStatus status;
    private T data;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return status;
    }

    public T getData() {
        return data;
    }

}
