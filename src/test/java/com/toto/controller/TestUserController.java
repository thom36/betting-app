package com.toto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.toto.repository.UserRepository;
import com.toto.service.UserService;

public class TestUserController {
    
    @Test
    void shouldReturn200WhenUserExists() throws Exception{
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        UserController controller = new UserController(service);

        FakeHttpExchange exchange = new FakeHttpExchange("/users/1");

        controller.handle(exchange);
        
        assertEquals(200, exchange.getResponseCode());
    }

    @Test
    void shouldReturn404UserNotFound() throws Exception{
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        UserController controller = new UserController(service);

        FakeHttpExchange exchange = new FakeHttpExchange("/users/20");

        controller.handle(exchange);

        assertEquals(404, exchange.getResponseCode());
    }
}
