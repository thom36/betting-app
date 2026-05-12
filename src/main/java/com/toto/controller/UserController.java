package com.toto.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.toto.domain.User;
import com.toto.dtos.UserResponse;
import com.toto.exceptions.UserNotFoundException;
import com.toto.service.IUserService;
import com.toto.utils.JsonSender;

public class UserController implements HttpHandler{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private IUserService service;

    public UserController(IUserService service){
        this.service = service;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if(method.matches("GET") && path.matches("/users/\\d+")){
            getUserById(exchange);
            return;
        }

        exchange.sendResponseHeaders(404, -1);
    }

    private void getUserById(HttpExchange exchange) throws IOException{
        try{
            String path = exchange.getRequestURI().getPath();
            int id = Integer.parseInt(path.substring("/users/".length()));
            if(id <= 0){
                JsonSender.sendJson(exchange, 400, "Invalid id", objectMapper);
                return;
            }

            User user = service.getUserById(id);
            
            UserResponse userResponse = new UserResponse(user.getName(), user.getEmail(), user.getSolde());
            JsonSender.sendJson(exchange, 200, userResponse, objectMapper);

        }catch (UserNotFoundException e) {
            JsonSender.sendJson(exchange, 404, e.getMessage(), objectMapper);
            
}       catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server error", objectMapper);
        }
    }
}
