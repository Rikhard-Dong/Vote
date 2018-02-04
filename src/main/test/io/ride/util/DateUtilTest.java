package io.ride.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void date2Str() {
        System.out.println(DateUtil.date2Str(new Date()));
    }

    @Test
    public void str2Date() throws ParseException {
        System.out.println(Integer.parseInt("1"));
        System.out.println(DateUtil.str2Date("2018-01-24 12:10:13"));
    }

    @Test
    public void calcTime() throws ParseException {
        Date date = DateUtil.str2Date("2018-1-25 12:00:00");
        System.out.println(DateUtil.calcTime(date));
    }
}