package com.ph.object.adapter;

public class Guard extends Player {

    public Guard(String name) {
        super(name);
    }

    public void attack() {
        System.out.println("后卫" + name + "进攻");
    }

    public void defense() {
        System.out.println("后卫" + name + "防守");
    }
}
