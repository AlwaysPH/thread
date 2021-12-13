package com.ph.object.adapter;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        Player player = new ForeignPlayer("姚明");
//        player.attack();
//        player.defense();
//
//        Player player1 = new Guard("麦迪");
//        player1.attack();
//        player1.defense();
        String s = "1,2,3";
        List<String> list = Arrays.asList(s.split(","));
        System.out.println(list);
    }
}
