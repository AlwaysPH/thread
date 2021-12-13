package com.ph.object.bridging;

public abstract class Phone {

    protected Soft soft;

    public abstract void run();

    public Phone(Soft soft) {
        this.soft = soft;
    }
}
