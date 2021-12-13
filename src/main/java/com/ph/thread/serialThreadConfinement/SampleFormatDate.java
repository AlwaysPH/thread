package com.ph.thread.serialThreadConfinement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleFormatDate {

    private static final ThreadLocal<SimpleDateFormat> TS_DF = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat();
        }
    };

    public static Date parse(String timeStamp, String format) throws ParseException {
        final SimpleDateFormat df = TS_DF.get();
        df.applyPattern(format);
        return df.parse(timeStamp);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(SampleFormatDate.parse("2020-12-25 10:44:50", "YYYY-MM-DD HH:mm:ss"));
    }
}
