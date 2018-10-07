package com.allometry.demo.model;

import lombok.Data;

@Data
public class Address
{

    private int id;
    private String city;
    private String state;
    private  String country;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
