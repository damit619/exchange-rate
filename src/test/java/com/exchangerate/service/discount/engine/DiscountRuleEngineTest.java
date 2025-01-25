package com.exchangerate.service.discount.engine;

import com.exchangerate.modle.bill.Category;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.service.discount.engine.rules.*;
import com.exchangerate.service.discount.RuleTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DiscountRuleEngineTest {

    private DiscountRuleEngine ruleEngine;

    @BeforeEach
    public void setUp () {
        ruleEngine = new DiscountRuleEngine(RuleTestData.getPercentageRules(), List.of(new FixedRule()));
    }

    @Test
    public void testRuleEngineShouldCalculatePercentageDiscount() {
        RuleData employeeData = RuleTestData.getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.EMPLOYEE);
        BigDecimal discount = ruleEngine.runPercentageRule(employeeData);
        assertThat("On Employee there should be 30% discount.", discount.doubleValue(), equalTo(136.2));
    }

    @Test
    public void testRuleEngineShouldNotApplyMultiplePercentageDiscount() {
        RuleData employeeData = RuleTestData.getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.EMPLOYEE);
        RuleData employeeData1 = RuleTestData.getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.EMPLOYEE);
        RuleData employeeData2 = RuleTestData.getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.EMPLOYEE);
        BigDecimal discount = Stream.of(
                        employeeData,
                        employeeData1,
                        employeeData2)
                .map(ruleData -> ruleEngine.runPercentageRule(ruleData))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat("On Employee there should be 30% discount.", discount.doubleValue(), equalTo(136.2));
    }

    @Test
    public void testRuleEngineShouldCalculateFixedDiscount() {
        BigDecimal discount = ruleEngine.runFixedRule(new BigDecimal(200));
        assertThat("Fixed rule should be 10 if amount is 200.", discount.doubleValue(), equalTo(10.0));
    }
}
