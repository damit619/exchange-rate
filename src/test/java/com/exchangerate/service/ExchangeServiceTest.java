package com.exchangerate.service;

import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.bill.CalculatedBillDetail;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.service.discount.DiscountService;
import com.exchangerate.service.exchangerate.ExchangeService;
import com.exchangerate.service.exchangerate.ExchangeServiceImpl;
import com.exchangerate.service.exchangerate.RateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.exchangerate.modle.CurrencyCode.INR;
import static com.exchangerate.modle.CurrencyCode.USD;
import static com.exchangerate.service.TestData.billDetail;
import static com.exchangerate.service.TestData.rateInfo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private RateServiceImpl rateService;
    @Mock
    private DiscountService discountService;

    private ExchangeService exchangeService;

    @BeforeEach
    public void setUp () {
        exchangeService = new ExchangeServiceImpl(rateService, discountService);
    }

    @Test
    public void testCalculateExchangeRateShouldReturnValidExchangeRate () {
        BillDetail billDetail = billDetail(USD, INR, new BigDecimal(200));
        when(discountService.calculateDiscount(billDetail)).thenReturn(new BigDecimal(5280));
        when(rateService.findTargetRate(USD, INR)).thenReturn(rateInfo(INR, new BigDecimal(88)));
        when(rateService.convert(any(RateInfo.class), any())).thenCallRealMethod();

        CalculatedBillDetail calculatedBillDetail = exchangeService.calculateExchangeRate(billDetail);

        assertThat("Calculated rate should be 12320", calculatedBillDetail.netAmount().doubleValue(), equalTo(12320.0));
        assertThat("Target currency should be INR", calculatedBillDetail.targetCurrency(), equalTo(INR));
    }

}
