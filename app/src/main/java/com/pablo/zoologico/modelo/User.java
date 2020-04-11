package com.pablo.zoologico.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {
    private String name;
    private String mail;
    private Date age;
    private String password;
    public static ArrayList<User>listUser=new ArrayList<>();

    public User() {
    }

    public User(String name, String mail, Date age, String password) {
        this.name = name;
        this.mail = mail;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}
