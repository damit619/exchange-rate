package com.exchangerate.openexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OpenExchangeRateDTO {
    private String base;
    private Map<String, BigDecimal> rates;
}
