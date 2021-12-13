package com.ph.thread.guardedSuspension;

public enum AlarmType {
    FAULT("fault"),RESUME("resume");

    private final String name;

    AlarmType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
