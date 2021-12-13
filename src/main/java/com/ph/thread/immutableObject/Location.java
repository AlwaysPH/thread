package com.ph.thread.immutableObject;

import java.io.Serializable;

public final class Location implements Serializable {

    private static final long serialVersionUID = -435851207753299698L;

    private String x;

    private String y;

    public Location(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
