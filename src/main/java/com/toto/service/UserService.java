package com.toto.service;

import com.toto.domain.User;
import com.toto.exceptions.UserNotFoundException;
import com.toto.repository.IUserRepository;

public class UserService implements IUserService{

    private IUserRepository repo;

    public UserService(IUserRepository repo){
        this.repo = repo;
    }

    public User getUserById(int id) {
        User user = repo.findById(id);
        if(user == null){
            throw new UserNotFoundException("User not found with id : " + id);
        }
        return user;
    }
}
