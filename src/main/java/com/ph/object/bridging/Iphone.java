package com.ph.object.bridging;

public class Iphone extends Phone {

    public Iphone(Soft soft) {
        super(soft);
    }

    public void run() {
        soft.run();
    }
}
