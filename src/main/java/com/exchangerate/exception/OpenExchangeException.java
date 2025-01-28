package com.exchangerate.exception;

import com.exchangerate.config.feign.FeignClientError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
public class OpenExchangeException extends RuntimeException {
    private String serviceName;
    private HttpStatus status;
    private String error;

    public static OpenExchangeException from(Response response) {
        if (null != response.body()) {
            try (BufferedReader reader = new BufferedReader(response.body().asReader(StandardCharsets.UTF_8))) {
                String httpBodyResponse = reader.lines()
                        .collect(Collectors.joining(""));
                ObjectMapper objectMapper = new ObjectMapper();
                FeignClientError error = objectMapper.readValue(httpBodyResponse, FeignClientError.class);

                return OpenExchangeException.builder()
                        .error(error.description())
                        .serviceName("ExchangeResource")
                        .status(HttpStatus.resolve(error.status()))
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return OpenExchangeException.builder()
                    .error("Internal server error")
                    .serviceName("ExchangeResource")
                    .status(HttpStatus.resolve(response.status()))
                    .build();
        }
    }

    public static boolean isClientError(Throwable throwable) {
        return throwable instanceof OpenExchangeException
                && ((OpenExchangeException) throwable).isClientError();
    }

    public static boolean isServerError(Throwable throwable) {
        return throwable instanceof OpenExchangeException
                && ((OpenExchangeException) throwable).isServerError();
    }

    public boolean isClientError() {
        return status.is4xxClientError();
    }

    public boolean isServerError() {
        return status.is5xxServerError();
    }
}
