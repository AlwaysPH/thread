package com.ph.object.reflect;

public class StudentServiceImpl implements StudentService {

    public String isBig(Student student, Integer age) {
        String result = "";
        if(student.getAge() > age){
            result = "嘿嘿，" + student.getName() + "比你大";
        }else {
            result = "呵呵，" + student.getName() + "比你小";
        }
        return result;
    }
}
