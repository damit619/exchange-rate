package com.exchangerate;

import java.util.UUID;

public final class TestUtil {

    private TestUtil() {}

    public static String randomStr (String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
