package com.toto.repository;

import com.toto.domain.User;

public interface IUserRepository {
    User findById(int id);
    User findByEmail(String email);
    User create(User user);
    int getNextUserId();
}
