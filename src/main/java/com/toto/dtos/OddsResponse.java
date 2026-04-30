package com.toto.dtos;

public class OddsResponse {
    public String description;
    public float value;

    public OddsResponse(String description, float value){
        this.description = description;
        this.value = value;
    }
}
