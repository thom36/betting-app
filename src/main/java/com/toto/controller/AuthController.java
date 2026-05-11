package com.toto.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.toto.dtos.AuthResponse;
import com.toto.dtos.CreateUserRequest;
import com.toto.dtos.LoginRequest;
import com.toto.service.AuthService;
import com.toto.utils.JsonSender;

public class AuthController implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if(method.matches("POST") && path.matches("/auth/register")){
            register(exchange);
            return;
        }
        if(method.matches("POST") && path.matches("/auth/login")){
            login(exchange);
            return;
        }

        exchange.sendResponseHeaders(404, -1);
    }

    private void register(HttpExchange exchange) throws IOException{
        try{
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                bodyBuilder.append(line);
            }
            String body = bodyBuilder.toString();

            if(body == null || body.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing Body", objectMapper);
                return;
            }

            JsonNode jsonNode = objectMapper.readTree(body);
            
            String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String passHash = jsonNode.get("passHash").asText();

            if(name == null || name.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing param name", objectMapper);
                return;
            }
            if(email == null || email.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing param email", objectMapper);
                return;
            }
            if(passHash == null || passHash.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing param passHash", objectMapper);
                return;
            }

            CreateUserRequest request = new CreateUserRequest();
            request.setName(name);
            request.setEmail(email);
            request.setPassHash(passHash);

            if(!request.isValidEmail()){
                JsonSender.sendJson(exchange, 400, "Invalid email", objectMapper);
                return;
            }

            AuthResponse createdUser = service.register(request.getName(), request.getEmail(), request.getPassHash());

            JsonSender.sendJson(exchange, 201, createdUser, objectMapper);

        }catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server error", objectMapper);
        }
    }

    private void login(HttpExchange exchange) throws IOException{
        try{
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                bodyBuilder.append(line);
            }
            String body = bodyBuilder.toString();

            if(body == null || body.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing Body", objectMapper);
                return;
            }

            JsonNode jsonNode = objectMapper.readTree(body);
            
            String email = jsonNode.get("email").asText();
            String passHash = jsonNode.get("passHash").asText();

            if(email == null || email.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing param email", objectMapper);
                return;
            }
            if(passHash == null || passHash.isBlank()){
                JsonSender.sendJson(exchange, 400, "Missing param passHash", objectMapper);
                return;
            }

            LoginRequest request = new LoginRequest();
            request.setEmail(email);
            request.setPassHash(passHash);

            AuthResponse connectedUser = service.login(request.getEmail(), request.getPassHash());

            JsonSender.sendJson(exchange, 200, connectedUser, objectMapper);

        }catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 401, "Invalid identifiers", objectMapper);
        }
    }
}
