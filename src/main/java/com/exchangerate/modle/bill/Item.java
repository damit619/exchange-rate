package com.exchangerate.modle.bill;

import lombok.Builder;

@Builder
public record Item (
        String name,
        String value,
        Category category
) {
    public boolean isGroceryItem () {
        return category == Category.GROCERY;
    }
}
