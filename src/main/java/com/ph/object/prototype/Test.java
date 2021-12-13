package com.ph.object.prototype;

public class Test {

    public static void main(String[] args) {
        Resume a = new Resume("哈哈");
        a.setPersonInfo("男", "18");
        a.setWorkExperience("2020-01", "嘻嘻");


        Resume b = (Resume) a.clone();
        b.setWorkExperience("2020-02", "呵呵");
        a.show();
        b.show();
    }
}
