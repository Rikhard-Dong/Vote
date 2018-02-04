package io.ride.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatCN = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

    public static String date2Str(Date date) {
        return format.format(date);
    }

    public static String date2StrCN(Date date) {
        return formatCN.format(date);
    }

    public static Date str2Date(String date) throws ParseException {
        return format.parse(date);
    }

    public static String calcTime(Date date) {
        Date curDate = new Date();
        long diff = Math.abs(date.getTime() - curDate.getTime());
        diff /= 1000;
        int hour = (int) (diff / 3600);
        int min = (int) (diff / 60 % 60);
        return hour + "小时" + min + "分钟";
    }


    /**
     * 判断是否超出时间
     *
     * @param data
     * @param ms
     * @return
     */
    public static boolean expire(Date data, long ms) {
        Date curDate = new Date();
        data.setTime(data.getTime() + ms);
        return curDate.after(data);
    }
}
