package com.ph.thread.guardedSuspension;

public class AlarmInfo {

    private String id;

    private String extraInfo;

    public final AlarmType type;

    public AlarmInfo(String id, String extraInfo, AlarmType type) {
        this.id = id;
        this.extraInfo = extraInfo;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
