package com.ph.object.adapter;

public class ForeignPlayer extends Player {

    private ForeignCenterForward foreignCenterForward = new ForeignCenterForward();

    public ForeignPlayer(String name) {
        super(name);
        foreignCenterForward.setName(name);
    }

    public void attack() {
        foreignCenterForward.attack();
    }

    public void defense() {
        foreignCenterForward.defense();
    }
}
