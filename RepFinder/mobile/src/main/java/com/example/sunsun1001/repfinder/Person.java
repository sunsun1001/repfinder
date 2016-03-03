package com.example.sunsun1001.repfinder;

/**
 * Created by sunsun1001 on 3/2/16.
 */
import java.io.Serializable;

public class Person implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;

    // getters & setters....

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
