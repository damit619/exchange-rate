package com.exchangerate.service.discount.engine.rule;

import com.exchangerate.modle.bill.Category;
import com.exchangerate.modle.user.UserType;
import com.exchangerate.service.discount.engine.rules.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.exchangerate.service.discount.RuleTestData.getRuleData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class RuleTest {

    @Test
    public void testEmployeeRuleIsApplicableIfUserTypeIsEmployeeAndItemCategoryIsNotGrocery () {
        assertThat("EmployeeRule should be applicable if userType is employee and Item Category is not grocery",
                new EmployeeRule().isApplicable(getRuleData(Category.ELECTRONICS, BigDecimal.TEN, UserType.EMPLOYEE))
                );
    }

    @Test
    public void testEmployeeRuleIsNotApplicableIfUserTypeIsEmployeeAndItemCategoryIsGrocery () {
        assertThat("EmployeeRule should not be applicable if userType is employee and Item Category is grocery",
                new EmployeeRule().isApplicable(getRuleData(Category.GROCERY, BigDecimal.TEN, UserType.EMPLOYEE)),
                is(false)
                );
    }

    @Test
    public void testCustomerRuleIsApplicableIfUserTypeIsCustomerAndItemCategoryIsNotGrocery () {
        assertThat("Customer should be applicable if userType is customer and Item Category is not grocery",
                new CustomerRule().isApplicable(getRuleData(Category.ELECTRONICS, BigDecimal.TEN, UserType.CUSTOMER))
                );
    }

    @Test
    public void testAffiliateRuleIsApplicableIfUserTypeIsAffiliateAndItemCategoryIsNotGrocery () {
        assertThat("Affiliate should be applicable if userType is affiliate and Item Category is not grocery",
                new AffiliateRule().isApplicable(getRuleData(Category.ELECTRONICS, BigDecimal.TEN, UserType.AFFILIATE))
                );
    }

    @Test
    public void testEmployeeDiscountShouldBe30 () {
        BigDecimal discount = new EmployeeRule().apply(getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.EMPLOYEE));
        assertThat("On Employee there should be 30% discount.", discount.doubleValue(), equalTo(136.2));
    }

    @Test
    public void testAffiliateDiscountShouldBe10 () {
        BigDecimal discount = new AffiliateRule().apply(getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.AFFILIATE));
        assertThat("On Customer there should be 10% discount.", discount.doubleValue(), equalTo(45.4));
    }

    @Test
    public void testCustomerDiscountShouldBe5 () {
        BigDecimal discount = new CustomerRule().apply(getRuleData(Category.ELECTRONICS, new BigDecimal(454), UserType.CUSTOMER));
        assertThat("On Customer there should be 5% discount.", discount.doubleValue(), equalTo(22.7));
    }

    @Test
    public void testFixedRuleIsApplicableShouldTrueIfAmountIsOver100() {
        FixedRule fixedRule = new FixedRule();
        assertThat("Fixed rule should be true if amount is over 100.", fixedRule.isApplicable(new BigDecimal(200)));
    }

    @Test
    public void testFixedRuleShouldApplyFlat5Discount() {
        FixedRule fixedRule = new FixedRule();
        assertThat("Fixed rule should be 10 if amount is 200.", fixedRule.apply(new BigDecimal(200)).doubleValue(), equalTo(10.0));
    }

}
