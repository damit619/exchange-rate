package com.exchangerate.web;

import com.exchangerate.web.dto.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage() + " is not supported");
        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(details)
                .path(request.getContextPath())
                .httpStatus(httpStatus)
                .build();

        logger.error("ResponseEntity | handleHttpRequestMethodNotSupported | ex : " + ex );

        return ResponseEntity.status(status).body(error);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
                    String errorMessage = err.getDefaultMessage();
                    details.add(errorMessage);
                }
        );
        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(details)
                .path(request.getContextPath())
                .httpStatus(httpStatus)
                .build();

        logger.error("ResponseEntity handleMethodArgumentNotValid | ex : " + ex );

        return new ResponseEntity<>(error, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {


        List<String> details = new ArrayList<String>();
        details.add(ex.getParameterName());

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(details)
                .path(request.getContextPath())
                .httpStatus(httpStatus)
                .build();

        logger.error("ResponseEntity | handleMissingServletRequestParameter | ex : " + ex );

        return ResponseEntity.status(status).body(error);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(details)
                .path(request.getContextPath())
                .httpStatus(httpStatus)
                .build();

        logger.error("ResponseEntity | handleHttpMessageNotReadable | ex : " + ex );

        return ResponseEntity.status(status).body(error);

    }
}
