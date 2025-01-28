package com.exchangerate.openexchange;

import com.exchangerate.mockhttpserver.AbstractClientTest;
import com.exchangerate.openexchange.dto.OpenExchangeRateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

import static com.exchangerate.service.TestData.getExchangeRates;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class ExchangeResourceTest extends AbstractClientTest {
    private static final String appId = UUID.randomUUID().toString();
    private static final String base = "USD";
    private static final String PATH = "/?app_id=" + appId + "&base=" + base;

    @Autowired
    private ExchangeResource exchangeResource;

    @Test
    public void testOpenExchangeVerifyData () {

        Map<String, String> headers = Map.of(
                CONTENT_TYPE, APPLICATION_JSON_VALUE,
                ACCEPT, APPLICATION_JSON_VALUE
        );
        OpenExchangeRateDTO openExchangeMock = getExchangeRates();
        mockServer.responseWith(HttpStatus.OK, openExchangeMock, headers)
                .call(() -> exchangeResource.getExchangeRateByBaseCurrency(appId, base))
                .expectResponse(openExchangeMock)
                .takeRequest()
                .expectPath(PATH)
                .expectMethod(GET.name());
    }

    @Test
    void testOpenExchangeShouldReturnClientErrorWhenServerRespondsWith4xxError() {
        mockServer.responseWith(HttpStatus.BAD_REQUEST)
                .call(() -> exchangeResource.getExchangeRateByBaseCurrency(appId, base))
                .expectClientError()
                .clearRequest();
    }

    @Test
    void testOpenExchangeShouldReturnServerErrorWhenServerRespondsWith5xxError() {
        mockServer.responseWith(HttpStatus.INTERNAL_SERVER_ERROR)
                .call(() -> exchangeResource.getExchangeRateByBaseCurrency(appId, base))
                .expectServerError()
                .clearRequest();
    }

}
