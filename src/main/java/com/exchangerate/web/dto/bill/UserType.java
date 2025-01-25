package com.exchangerate.web.dto.bill;

import java.util.Arrays;
import java.util.Optional;

public enum UserType {
    EMPLOYEE("employee"),
    CUSTOMER("customer"),
    AFFILIATE("affiliate");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public Optional<UserType> getUserType (String strUserType) {
        return Arrays.stream(values())
                .filter(enumVal -> enumVal.type.equalsIgnoreCase(strUserType))
                .findFirst();
    }
}
