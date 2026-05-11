package com.toto.repository;

import java.util.ArrayList;
import java.util.List;

import com.toto.domain.User;

public class FakeUserDB {

    private static FakeUserDB dbInstance;
    private static final List<User> users = new ArrayList<>();

    static {
        User user1 = new User(1, "Thomas", "thomas.messina@gmail.com", "totodu38");
        User user2 = new User(2, "Sarah", "sarah.dupont@gmail.com", "sarah123");
        User user3 = new User(3, "Lucas", "lucas.martin@gmail.com", "lucasdev");
        User user4 = new User(4, "Emma", "emma.bernard@gmail.com", "emma_pro");
        User user5 = new User(5, "Nina", "nina.robert@gmail.com", "nina789");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }

    private FakeUserDB() {
    }

    public static FakeUserDB getInstance() {
        if (dbInstance == null) {
            dbInstance = new FakeUserDB();
        }
        return dbInstance;
    }

    public List<User> getUsers() {
        return users;
    }
}