package com.test.kopnus.handler;


import com.test.kopnus.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.RejectedExecutionException;

@Component
@ControllerAdvice
public class GlobalExeptionHandler {
    Logger loggerFactory = LoggerFactory.getLogger("GlobalExceptionHandler");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception ex, HttpServletRequest request)
    {
        loggerFactory.error("==================GLOBAL START ERROR=================");
        if(ex instanceof MissingRequestHeaderException){
            loggerFactory.error("Error MissingRequstHeaderException");
        }
        else if((ex instanceof SQLException)){
            loggerFactory.error("Error SQLException");
        }

        loggerFactory.error("Remote Host: " + request.getRemoteHost());
        loggerFactory.error("Request Method: " + request.getMethod());
        loggerFactory.error("Request URI: " + request.getRequestURI());
        loggerFactory.error("Request Param: " + request.getQueryString());

        //request.get
        //loggerFactory.error("Error throw by [" + ex.getClass().getSimpleName()+"]");
        loggerFactory.error("Message : "+ex.getMessage());
        loggerFactory.error("==================GLOBAL END ERROR=================");
        return ErrorResponse.generateError(ex);
    }
    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<?> notAuthorized(RejectedExecutionException ex, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), ex.getStackTrace().toString(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
