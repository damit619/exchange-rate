package com.exchangerate.mockhttpserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MockServer {

    private final ObjectWriter objectWriter;
    private final MockWebServer mockWebServer;

    private MockServer() {
        this.objectWriter = new ObjectMapper().writer();
        this.mockWebServer = new MockWebServer();
    }

    public static MockServer create () {
        return new MockServer();
    }

    public void destroy () {
        try {
            mockWebServer.shutdown();
        } catch (IOException ignored) {}
    }

    public String getMockServerUrl() {
        return mockWebServer.url("").toString();
    }

    private <T> String toJson(T value) {
        try {
            return objectWriter.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MockServer responseWith(HttpStatus status) {
        mockWebServer.enqueue(new MockResponse().setResponseCode(status.value()));
        return this;
    }

    public <T> MockServer responseWith(HttpStatus status, T responseBody, Map<String, String> headers) {
        MockResponse response = new MockResponse()
                .setResponseCode(status.value())
                .setBody(toJson(responseBody));
        headers.forEach(response::addHeader);
        mockWebServer.enqueue(response);
        return this;
    }

    public <T> ResponseVerifier call(Supplier<T> clientDelegate) {
        return new ResponseVerifier(this, clientDelegate);
    }

    public RequestVerifier takeRequest() {
        try {
            RecordedRequest request = mockWebServer.takeRequest(1000, TimeUnit.MILLISECONDS);
            return new RequestVerifier(request);
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void clearRequest() {
        try {
            mockWebServer.takeRequest(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
