package com.abocidee.newdemo.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class 当前时间 {
    public static String get() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
