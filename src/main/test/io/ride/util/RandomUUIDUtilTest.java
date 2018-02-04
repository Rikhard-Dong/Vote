package io.ride.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class RandomUUIDUtilTest {

    @Test
    public void test() {
        System.out.println(RandomUUIDUtil.getRandomId(new Date().toString()));
    }

}