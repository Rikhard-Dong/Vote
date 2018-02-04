package io.ride.util;

import java.util.UUID;

public class RandomUUIDUtil {

    public static String getRandomId(String key) {
        return UUID.nameUUIDFromBytes(key.getBytes()).toString().replace("-", "");
    }
}
