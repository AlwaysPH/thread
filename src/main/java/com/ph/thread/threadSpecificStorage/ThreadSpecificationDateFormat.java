package com.ph.thread.threadSpecificStorage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadSpecificationDateFormat {

    private static final ThreadLocal<SimpleDateFormat> TS_SFT = new ThreadLocal<SimpleDateFormat>(){
      protected SimpleDateFormat initialValue(){
          return new SimpleDateFormat();
      }
    };

    public static Date parseDate(String dateStr, String format) throws ParseException {
        final SimpleDateFormat sdf = TS_SFT.get();
        sdf.applyPattern(format);
        return sdf.parse(dateStr);
    }

    public static void main(String[] args) throws ParseException {
        Date date = ThreadSpecificationDateFormat.parseDate("2020-12-10 20:04:00", "yyyy-MM-dd HH:ss:mm");
        System.out.println(date);
    }
}
