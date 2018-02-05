package io.ride.test;

import io.ride.util.JacksonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        List<List<Object>> data = new ArrayList<>();
        List<Object> lists1 = new ArrayList<>();
        lists1.add("a");
        lists1.add(12);

        List<Object> lists2 = new ArrayList<>();
        lists2.add("b");
        lists2.add(32);

        List<Object> lists3 = new ArrayList<>();
        lists3.add("c");
        lists3.add(56);

        data.add(lists1);
        data.add(lists2);
        data.add(lists3);


        System.out.println(JacksonUtil.toJSon(data));
    }


}
