package com.exchangerate.web.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BillDetailResponseDTO {

    private BigDecimal netAmount;
    private CurrencyCode targetCurrency;
}
