package com.exchangerate.modle.bill;

import java.util.Arrays;
import java.util.Optional;

public enum Category {

    GROCERY("grocery"),
    ELECTRONICS("electronics");

    private final String value;

    Category (String value) {
        this.value = value;
    }

    public Optional<Category> getCategory (String strCategory) {
        return Arrays.stream(values())
                .filter(enumVal -> enumVal.value.equalsIgnoreCase(strCategory))
                .findFirst();
    }

}
