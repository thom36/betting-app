package com.toto;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.toto.controller.AuthController;
import com.toto.controller.MatchController;
import com.toto.controller.UserController;
import com.toto.repository.MatchRepository;
import com.toto.repository.UserRepository;
import com.toto.service.AuthService;
import com.toto.service.JwtService;
import com.toto.service.MatchService;
import com.toto.service.UserService;


public class Main 
{
    public static void main( String[] args ) throws IOException
    {
        MatchRepository matchRepo = new MatchRepository();
        MatchService matchService = new MatchService(matchRepo);
        MatchController matchController = new MatchController(matchService);
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        UserController userController = new UserController(userService);
        AuthService authService = new AuthService(userRepo, new JwtService());
        AuthController authController = new AuthController(authService);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/matches", matchController);
        server.createContext("/users", userController);
        server.createContext("/auth", authController);
        server.setExecutor(null);
        server.start();
        
        System.out.println("Server is running on the port 8080");
    }
}
