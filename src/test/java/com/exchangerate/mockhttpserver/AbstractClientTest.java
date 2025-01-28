package com.exchangerate.mockhttpserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
public abstract class AbstractClientTest {

    private static final String OPEN_EXCHANGE_URL = "exchange-rate.open-exchange-api.api-url";

    protected static MockServer mockServer;

    @BeforeAll
    public static void setup() {
       mockServer = MockServer.create();
    }

    @AfterAll
    public static void tearDown () {
        mockServer.destroy();
    }

    @DynamicPropertySource
    public static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add(OPEN_EXCHANGE_URL, mockServer::getMockServerUrl);
    }



}
