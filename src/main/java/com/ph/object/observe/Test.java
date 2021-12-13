package com.ph.object.observe;

public class Test {

    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.setStates("我回来了");

        NBAMember nbaMember = new NBAMember("哈哈", boss);
        StockMember stockMember = new StockMember("嘻嘻", boss);
        boss.add(nbaMember);
        boss.add(stockMember);

        boss.notifyAllObserver();
    }
}
