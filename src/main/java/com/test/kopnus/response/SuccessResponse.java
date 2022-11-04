package com.test.kopnus.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.LinkedHashMap;

public class SuccessResponse<T> {
    Date timestamp;
    int statusCode;
    String message;
    T data;
    public static<T> SuccessResponse<T> getInstance(T data)
    {
        return new SuccessResponse<>(data);
    }
    public static SuccessResponse getInstance(String message){
        return new SuccessResponse(message);
    }
    public SuccessResponse(){

    }
    public SuccessResponse(Date timestamp, int statusCode, String message, T data)
    {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(String message)
    {
        this(new Date(), HttpStatus.OK.value(), message, null);
    }
    public SuccessResponse(T data)
    {
        this(new Date(), HttpStatus.OK.value(), "success", data);
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public int getErrorCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static<T> ResponseEntity generateSuccessResponse(T data){
        SuccessResponse<T> response = new SuccessResponse<>(new Date(), HttpStatus.OK.value(), "success", data);
        return ResponseEntity.ok().body(response);
    }

    public static<T1, T2> ResponseEntity generateSuccessResponse(T1 fieldName, T2 data){
        LinkedHashMap<T1, T2> result = new LinkedHashMap<T1, T2>();
        result.put(fieldName, data);
        SuccessResponse<LinkedHashMap<T1, T2>> response = new SuccessResponse<>(new Date(), HttpStatus.OK.value(), "success", result);
        return ResponseEntity.ok().body(response);
    }
}
