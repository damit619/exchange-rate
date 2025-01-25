package com.exchangerate.service.discount;

import com.exchangerate.modle.bill.BillDetail;

import java.math.BigDecimal;

public interface DiscountService {

    BigDecimal calculateDiscount (BillDetail billDetail);
}
