package com.allometry.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    private int id;
    private String name;
    private String school;


    @Override
    public String
    toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
