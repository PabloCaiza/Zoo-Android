package com.pablo.zoologico.modelo;

import android.graphics.Bitmap;

public class Animal {
    private String name;
    private String type;
    private String habit;
    private byte[] image;

    public Animal() {
    }


    public Animal(String name, String type, String habit, byte[] image) {
        this.name = name;
        this.type = type;
        this.habit = habit;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", habit='" + habit + '\'' +
                '}';
    }
}
