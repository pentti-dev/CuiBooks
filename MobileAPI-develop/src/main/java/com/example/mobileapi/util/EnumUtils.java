package com.example.mobileapi.util;

import java.util.concurrent.ThreadLocalRandom;

public class EnumUtils {
    public static <T extends Enum<T>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int idx = ThreadLocalRandom.current().nextInt(values.length);
        return values[idx];
    }

}
