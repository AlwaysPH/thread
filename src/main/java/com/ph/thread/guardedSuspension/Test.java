package com.ph.thread.guardedSuspension;

public class Test {

    public static void main(String[] args) throws Exception {
        AlarmAgent agent = new AlarmAgent();
        agent.init();
        AlarmInfo info = new AlarmInfo("1", "警告警告", AlarmType.RESUME);
        System.out.println(agent.sendAlarmInfo(info));
    }
}
