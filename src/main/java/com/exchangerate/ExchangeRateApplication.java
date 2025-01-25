package com.exchangerate;

import com.exchangerate.config.ExchangeApiProperties;
import com.exchangerate.openexchange.ExchangeResource;
import com.exchangerate.openexchange.dto.OpenExchangeRateDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner runner (ExchangeResource exchangeResource, ExchangeApiProperties exchangeApiProperties) {
		return args -> {
			OpenExchangeRateDTO openExchangeRateDTO = exchangeResource.getExchangeRateByBaseCurrency(exchangeApiProperties.apiId(), "USD");
			System.out.println(openExchangeRateDTO);
		};
	}*/
}
