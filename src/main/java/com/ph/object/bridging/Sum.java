package com.ph.object.bridging;

public class Sum extends Phone {
    public Sum(Soft soft) {
        super(soft);
    }

    public void run() {
        soft.run();
    }
}
