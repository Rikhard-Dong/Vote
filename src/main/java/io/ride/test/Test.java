package io.ride.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        String str = "<io.ride.wechat.PO.wechat.response.Article>";
        str = str.replaceAll("io.ride.wechat.PO.wechat.response.Article", "item");
        System.out.println(str);
    }


}
