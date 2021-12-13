package com.ph.object.observe;

public class NBAMember extends Observer {

    public NBAMember(String name, Subject subject) {
        super(name, subject);
    }

    public void update() {
        System.out.println(name + "关闭NBA直播，认真工作!");
    }
}
