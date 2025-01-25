package com.exchangerate.modle.user;

import java.util.List;

public record User (
        String uuid,
        String userName,
        String password,
        List<UserType> userTypes,
        Integer tenure
) { }
