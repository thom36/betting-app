package com.toto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.toto.domain.User;

public class TestUserRepository {
    
    @Test
    void shouldFindById(){
        UserRepository repo = new UserRepository();
        User user = repo.findById(1);
        assertNotNull(user);
        assertEquals("Thomas", user.getName());
    }
}
