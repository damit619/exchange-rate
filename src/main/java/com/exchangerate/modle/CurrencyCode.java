package com.exchangerate.modle;

import java.util.Arrays;

public enum CurrencyCode {

    AED("United Arab Emirates Dirham"),
    AFN("Afghan Afghani"),
    ALL("Albanian Lek"),
    AMD("Armenian Dram"),
    ANG("Netherlands Antillean Guilder"),
    AOA("Angolan Kwanza"),
    ARS("Argentine Peso"),
    INR("Indian Rupee"),
    USD("USD");

    private final String currencyDesc;

    CurrencyCode(String description) {
        this.currencyDesc = description;
    }

    public String getCurrencyDesc() {
        return currencyDesc;
    }

    public static CurrencyCode getCurrencyCode(String source) {
        return CurrencyCode.valueOf(source.toUpperCase());
    }

    public static boolean isCurrencyCodeValid(String source) {
        return Arrays.stream(values())
                .anyMatch(code -> code.name().equalsIgnoreCase(source));
    }
}
