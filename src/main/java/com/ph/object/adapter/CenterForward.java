package com.ph.object.adapter;

public class CenterForward extends Player {

    public CenterForward(String name) {
        super(name);
    }

    public void attack() {
        System.out.println("中锋" + name + "进攻");
    }

    public void defense() {
        System.out.println("中锋" + name + "防守");
    }
}
