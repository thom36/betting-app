package com.toto.domain;

public class Odd {
    private int id;
    private String description;
    private float value;

    public Odd(int oddId, String description, float value){
        this.id = oddId;
        this.description = description;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public float getValue() {
        return value;
    }
}
