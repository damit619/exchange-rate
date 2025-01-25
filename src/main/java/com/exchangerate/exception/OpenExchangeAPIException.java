package com.exchangerate.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OpenExchangeAPIException extends RuntimeException {
    private String serviceName;
    private HttpStatus statusCode;
    private String error;
}
