package com.ph.object.adapter;

public class Forward extends Player {

    public Forward(String name) {
        super(name);
    }

    public void attack() {
        System.out.println("前锋" + name + "进攻");
    }

    public void defense() {
        System.out.println("前锋" + name + "防守");
    }
}
