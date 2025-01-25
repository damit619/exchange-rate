package com.exchangerate.service.discount.engine.rules;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public abstract class PercentageRule implements DiscountRule <RuleData, BigDecimal>{
    private boolean isApplied;

    @Override
    public BigDecimal apply(RuleData item) {
        if (item.billTotal().compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        this.isApplied = true;
        MathContext roundCtx = new MathContext(5, RoundingMode.CEILING);
        return item.billTotal().multiply(percentage(), roundCtx).divide(new BigDecimal(100), roundCtx);
    }

    public boolean isApplied() {
        return isApplied;
    }
    protected abstract BigDecimal percentage ();

}
