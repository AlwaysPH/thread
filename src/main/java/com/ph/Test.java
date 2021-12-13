package com.ph;

public class Test {
    private static String getPepoleRate(Object data) {
        String str = data.toString();
        if(str.contains(".")){
            String[] strPoint = str.split("\\.");
            String d1 = strPoint[0];
            String d2 = strPoint[1].substring(0, 2);
            return d1 + "." + d2;
        }else {
            return str;
        }
    }


    public static void main(String[] args) {
        System.out.println(getPepoleRate("3.215487"));
    }
}
