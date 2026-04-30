package com.toto;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.toto.controller.MatchController;
import com.toto.repository.MatchRepository;
import com.toto.service.MatchService;


public class Main 
{
    public static void main( String[] args ) throws IOException
    {
        MatchRepository matchRepo = new MatchRepository();
        MatchService matchService = new MatchService(matchRepo);
        MatchController matchController = new MatchController(matchService);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/matches", matchController);
        server.setExecutor(null);
        server.start();
        
        System.out.println("Server is running on the port 8080");
    }
}
