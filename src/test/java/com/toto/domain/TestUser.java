package com.toto.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUser {
    
    @Test
    void shouldCreateUser(){
        User user = new User(1, "Lucas", "lucas.messina@gmail.com", "hash");
        assertEquals(1, user.getId());
        assertEquals("Lucas", user.getName());
        assertEquals("lucas.messina@gmail.com", user.getEmail());
        assertEquals(100.0, user.getSolde());
    }
}
