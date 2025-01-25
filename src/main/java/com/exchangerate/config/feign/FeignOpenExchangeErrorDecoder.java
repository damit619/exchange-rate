package com.exchangerate.config.feign;

import com.exchangerate.exception.OpenExchangeAPIException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class FeignOpenExchangeErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String key, Response response) {
        try (BufferedReader reader = new BufferedReader(response.body().asReader(StandardCharsets.UTF_8))) {
            String httpBodyResponse = reader.lines()
                    .collect(Collectors.joining(""));
            ObjectMapper objectMapper = new ObjectMapper();
            FeignClientError error = objectMapper.readValue(httpBodyResponse, FeignClientError.class);

            return OpenExchangeAPIException.builder()
                    .error(error.description())
                    .serviceName("OpenExchangeRateApi")
                    .statusCode(HttpStatus.resolve(error.status()))
                    .build();
        }
    }
}
