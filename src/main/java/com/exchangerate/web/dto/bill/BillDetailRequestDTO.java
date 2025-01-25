package com.exchangerate.web.dto.bill;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.bill.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillDetailRequestDTO {
    private List<Item> items;
    private BigDecimal totalAmount;
    private CurrencyCode base;
    private CurrencyCode target;
    private UserType userType;
    private Integer tenure;
}
