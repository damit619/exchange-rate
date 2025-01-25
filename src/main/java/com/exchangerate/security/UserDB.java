package com.exchangerate.security;

import com.exchangerate.modle.user.User;
import com.exchangerate.modle.user.UserType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class UserDB {

    private static final Map<String, User> USER_DB = new HashMap<>();

    static {
        User userEmp = getUser("user1", UserType.EMPLOYEE, 1);
        User userCust1 = getUser("user2", UserType.CUSTOMER, 3);
        User userCust2 = getUser("user3", UserType.CUSTOMER, 1);
        User userAff = getUser("user4", UserType.AFFILIATE, 1);

        USER_DB.put(userEmp.uuid(), userEmp);
        USER_DB.put(userCust1.uuid(), userCust1);
        USER_DB.put(userCust2.uuid(), userCust2);
        USER_DB.put(userAff.uuid(), userAff);
    }

    private static User getUser(String userName, UserType userType, Integer tenure) {
        return new User(UUID.randomUUID().toString(), userName, encryptPassword(), List.of(userType), tenure);
    }

    public static Map<String, User> getUserDb () {
        return USER_DB;
    }

    private static String encryptPassword () {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("password");
    }
}
