package io.ride.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptionUtilTest {

    @Test
    public void md5() {
        System.out.println(EncryptionUtil.md5("admin"));
    }
}