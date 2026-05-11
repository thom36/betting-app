package com.toto.dtos;

public class UserResponse {
    public String name;
    public String email;
    public float solde;

    public UserResponse(String name, String email, float solde){
        this.name = name;
        this.email = email;
        this.solde = solde;
    }
}
