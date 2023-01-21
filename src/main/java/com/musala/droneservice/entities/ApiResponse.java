package com.musala.droneservice.entities;

import org.springframework.http.HttpStatus;

public class ApiResponse<T>
{
    public ApiResponse(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponse(String message, HttpStatus statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    private String message;
    private HttpStatus statusCode;
    private T data;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }
}
