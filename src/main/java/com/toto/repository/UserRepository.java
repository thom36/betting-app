package com.toto.repository;

import com.toto.domain.User;

public class UserRepository implements IUserRepository{
    private FakeUserDB db;

    public UserRepository(){
        this.db = FakeUserDB.getInstance();
    }

    public User findById(int id) {
        return db.getUsers().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User findByEmail(String email) {
        return db.getUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User create(User user) {
        db.getUsers().add(user);
        return user;
    }

    public int getNextUserId(){
        return db.getUsers().size() + 1;
    }
}
