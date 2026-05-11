package com.toto.dtos;

import java.util.regex.Pattern;

public class CreateUserRequest {
    private String name;
    private String email;
    private String passHash;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassHash(){
        return passHash;
    }
    public void setPassHash(String passHash){
        this.passHash = passHash;
    }

    public boolean isValidEmail(){
        return Pattern.compile("^(.+)@(\\S+)$")
                    .matcher(email)
                    .matches();
    }
}
