package com.exchangerate.service.discount.engine.rules;

import java.math.BigDecimal;

public interface DiscountRule<T, R> {

    boolean isApplicable(T t);

    R apply(T t);
}
