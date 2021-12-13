package com.ph.object.observe;

public class StockMember extends Observer {

    public StockMember(String name, Subject subject) {
        super(name, subject);
    }

    public void update() {
        System.out.println(name + "关闭股票APP，认真工作!");
    }
}
