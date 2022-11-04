package com.test.kopnus.response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;


public class ErrorResponse {
    Date timestamp;
    int errorCode;
    String message;
    String details;
    Object data;
    public ErrorResponse(Date timestamp, int errorCode, String message, String details, Object data)
    {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setData(Object data){this.data = data;}
    public Object getData(){return this.data;}
    public static ResponseEntity generateError(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ExceptionUtils.getStackTrace(ex), null));
    }

    public static ResponseEntity generateNotFoundResponse(String detailsMessage){
        ErrorResponse notFound = new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Data not found", detailsMessage, new Object());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
    }

    public static<T> ResponseEntity generateNotModified(T data){
        ErrorResponse notFound = new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Data not modified/not saved", "", data);
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(notFound);
    }

    public static ResponseEntity generateResponseWithStatus(String detailMessage, int statusCode, String className){
        ErrorResponse response400 = new ErrorResponse(new Date(), statusCode, detailMessage, className, null);
        return ResponseEntity.status(statusCode).body(response400);
    }
}
