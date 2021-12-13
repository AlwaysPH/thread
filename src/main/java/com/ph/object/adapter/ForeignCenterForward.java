package com.ph.object.adapter;

public class ForeignCenterForward {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void attack() {
        System.out.println("外籍中锋" + name + "进攻");
    }

    public void defense() {
        System.out.println("外籍中锋" + name + "防守");
    }

}
