package com.exchangerate.service.exchangerate;

import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.CalculatedBillDetail;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.service.discount.DiscountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    private final RateService rateService;
    private final DiscountService discountService;

    @Override
    public CalculatedBillDetail calculateExchangeRate(BillDetail billDetail) {
        BigDecimal discountedAmount = discountService.calculateDiscount(billDetail);
        RateInfo billTargetRate = rateService.findTargetRate(billDetail.base(), billDetail.target());
        BigDecimal billTotalAmount = rateService.convert(billTargetRate, billDetail.totalAmount());

        return new CalculatedBillDetail(billTotalAmount.subtract(discountedAmount), billTargetRate.currencyCode());
    }

}
