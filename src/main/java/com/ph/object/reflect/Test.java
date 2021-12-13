package com.ph.object.reflect;

public class Test {

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("小明");
        student.setAge(18);

        Integer age = 16;
        StudentService service = new ReflectService(new StudentServiceImpl()).getProxy();
        System.out.println(service.isBig(student, age));
    }
}
