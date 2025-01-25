package com.exchangerate.web;

import com.exchangerate.exception.ExchangeNotFoundException;
import com.exchangerate.exception.OpenExchangeAPIException;
import com.exchangerate.web.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({OpenExchangeAPIException.class})
    public ResponseEntity<ErrorDetail> openExchangeError (OpenExchangeAPIException openExchangeAPIException) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(List.of(openExchangeAPIException.getError()))
                .httpStatus(openExchangeAPIException.getStatusCode())
                .build();
        return new ResponseEntity<>(errorDetail, openExchangeAPIException.getStatusCode());
    }

    @ExceptionHandler({ExchangeNotFoundException.class})
    public ResponseEntity<ErrorDetail> exchangeNotFound (ExchangeNotFoundException exchangeNotFoundException) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(List.of(exchangeNotFoundException.getMessage()))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(errorDetail, errorDetail.httpStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .errorDetails(List.of(ex.getMessage()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(errorDetail, errorDetail.httpStatus());
    }
}
