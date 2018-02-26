package io.ride.test;

import io.ride.util.JacksonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Test {
    public static void main(String[] args) {

        double a = 17.12;
        double b = 17.120001;

        System.out.println(a == b);
    }


}
