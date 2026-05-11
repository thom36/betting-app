package com.toto.domain;

public class User {
    private int id;
    private String name;
    private String email;
    private String passHash;
    private float solde;

    public User(int userId, String name, String email, String passHash){
        this.id = userId;
        this.name = name;
        this.email = email;
        this.passHash = passHash;
        this.solde = 100;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassHash(){
        return passHash;
    }

    public float getSolde(){
        return solde;
    }
}
