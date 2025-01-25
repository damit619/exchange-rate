package com.exchangerate.service.discount;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.service.TestData;
import com.exchangerate.service.discount.engine.DiscountRuleEngine;
import com.exchangerate.service.discount.engine.rules.FixedRule;
import com.exchangerate.service.exchangerate.RateService;
import com.exchangerate.service.exchangerate.RateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.exchangerate.modle.CurrencyCode.INR;
import static com.exchangerate.modle.CurrencyCode.USD;
import static com.exchangerate.service.TestData.rateInfo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    @Mock
    private RateServiceImpl rateService;

    private DiscountService discountService;

    @BeforeEach
    public void setUp () {
        DiscountRuleEngine ruleEngine = new DiscountRuleEngine(RuleTestData.getPercentageRules(), List.of(new FixedRule()));
        discountService = new DiscountServiceImpl(ruleEngine, rateService);
    }

    @Test
    public void testCalculateDiscount () {
        when(rateService.findTargetRate(USD, INR)).thenReturn(rateInfo(INR, new BigDecimal(88)));
        when(rateService.findTargetRate(USD, USD)).thenReturn(rateInfo(USD, new BigDecimal(0)));
        when(rateService.convert(any(RateInfo.class), any())).thenCallRealMethod();
        BigDecimal totalDiscount = discountService.calculateDiscount(TestData.billDetail(USD, INR, new BigDecimal(200)));
        assertThat("Total discount in target currency is 5280.", totalDiscount.doubleValue(), equalTo(5280.0));
    }


}
