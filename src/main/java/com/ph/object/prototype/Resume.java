package com.ph.object.prototype;

import java.io.Serializable;

public class Resume implements Serializable, Cloneable {

    private static final long serialVersionUID = 7134361947744540228L;

    private String name;

    private String sex;

    private String age;

    private WorkExperience workExperience;

    public Resume(String name) {
        this.name = name;
        workExperience = new WorkExperience();
    }

    public Resume(WorkExperience experience){
        this.workExperience = (WorkExperience) experience.clone();
    }

    public void setPersonInfo(String sex, String age){
        this.sex = sex;
        this.age = age;
    }

    public void setWorkExperience(String workDate, String company){
        workExperience.setWorkDate(workDate);
        workExperience.setWorkCompany(company);
    }

    public void show(){
        System.out.println("姓名：" + name + ", 年龄：" + age + ", 性别：" + sex);
        System.out.println("工作经历：" + workExperience.getWorkDate() + "~" + workExperience.getWorkCompany());
    }

    //浅复制
//    @Override
//    public Object clone(){
//        Object result = null;
//        try {
//            result = super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @Override
    public Object clone(){
        Resume resume = new Resume(workExperience);
        this.name = name;
        this.sex = sex;
        this.age = age;
        return resume;
    }
}
