package com.ph.object.bridging;

public class Test {
    public static void main(String[] args) {
        String s = "abc123";
        System.out.println(s.split("\\.").length > 1 ? s.split("\\.")[1] : s);
    }
}
