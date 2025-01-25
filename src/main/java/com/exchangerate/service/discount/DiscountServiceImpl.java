package com.exchangerate.service.discount;

import com.exchangerate.modle.CurrencyCode;
import com.exchangerate.modle.bill.BillDetail;
import com.exchangerate.modle.exchange.RateInfo;
import com.exchangerate.service.discount.engine.DiscountRuleEngine;
import com.exchangerate.service.discount.engine.rules.RuleData;
import com.exchangerate.service.exchangerate.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRuleEngine discountRuleEngine;
    private final RateService rateService;

    @Override
    public BigDecimal calculateDiscount(BillDetail billDetail) {
        RateInfo usdRate = rateService.findTargetRate(billDetail.base(), CurrencyCode.USD);
        RateInfo billTargetRate = rateService.findTargetRate(billDetail.base(), billDetail.target());
        BigDecimal usdAmount = rateService.convert(usdRate, billDetail.totalAmount());

        BigDecimal percentageTargetAmt = rateService.convert(billTargetRate, getPercentageDiscount(billDetail));
        BigDecimal fixedTargetAmt = rateService.convert(billTargetRate, discountRuleEngine.runFixedRule(usdAmount));

        return percentageTargetAmt.add(fixedTargetAmt);
    }

    private BigDecimal getPercentageDiscount(BillDetail billDetail) {
        return getItemRuleData(billDetail).stream()
                .map(discountRuleEngine::runPercentageRule)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<RuleData> getItemRuleData (BillDetail billDetail) {
        return billDetail.items().stream()
                .map(item -> new RuleData(item, billDetail.totalAmount(), billDetail.userType(), billDetail.tenure()))
                .toList();
    }

}
