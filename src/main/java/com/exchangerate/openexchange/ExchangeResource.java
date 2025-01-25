package com.exchangerate.openexchange;

import com.exchangerate.config.feign.FeignClientConfig;
import com.exchangerate.openexchange.dto.OpenExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "open-exchange-api",
        url = "${exchange-rate.open-exchange-api.api-url}",
        configuration = FeignClientConfig.class)
public interface ExchangeResource {

    @GetMapping("?app_id={apiId}&base={baseCurrency}")
    public OpenExchangeRateDTO getExchangeRateByBaseCurrency (@RequestParam("app_id") String apiId, @RequestParam("base") String baseCurrency);
}
