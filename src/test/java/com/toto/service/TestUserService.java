package com.toto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.toto.domain.User;
import com.toto.exceptions.UserNotFoundException;
import com.toto.repository.UserRepository;

public class TestUserService {

    @Test
    void shouldGetUserById(){
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        User user = service.getUserById(1);
        assertNotNull(user);
        assertEquals("Thomas", user.getName());
    }

    @Test
    void shouldNotGetUserById(){
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        
        UserNotFoundException exception = assertThrows(
        UserNotFoundException.class,
            () -> service.getUserById(20)
        );

        assertEquals(
            "User not found with id : 20",
            exception.getMessage()
        );
    }
    
}
