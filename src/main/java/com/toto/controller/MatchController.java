package com.toto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.toto.domain.Match;
import com.toto.domain.Odd;
import com.toto.dtos.MatchResponse;
import com.toto.dtos.MatchesBySportRequest;
import com.toto.dtos.MatchesByTeamRequest;
import com.toto.dtos.OddsResponse;
import com.toto.service.IMatchService;
import com.toto.utils.JsonSender;
import com.toto.utils.MapOddsResponse;

public class MatchController implements HttpHandler{
    private IMatchService service;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public MatchController(IMatchService service){
        this.service = service;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if(method.matches("GET") && path.matches("/matches/sports")){
            getMatchesBySport(exchange);
            return;
        }
        if(method.matches("GET") && path.matches("/matches/odds/\\d+")){
            getOddsByMatchId(exchange);
            return;
        }
        if(method.matches("GET") && path.matches("/matches/\\d+")){
            getMatchById(exchange);
            return;
        }
        if(method.matches("GET") && path.matches("/matches/teams")){
            getMatchesByTeam(exchange);
            return;
        }

        exchange.sendResponseHeaders(404, -1);
    }

    private void getMatchesBySport(HttpExchange exchange) throws IOException{
        try{
            MatchesBySportRequest request = 
                objectMapper.readValue(
                    exchange.getRequestBody(),
                MatchesBySportRequest.class
                );
            if(request == null || !request.isValid()){
                JsonSender.sendJson(exchange, 400, "Missing field or invalid : sport", objectMapper);
            }

            List<Match> matches = service.getMatchesBySport(request.getSport());
            List<MatchResponse> response = new ArrayList<>();
            for(Match m : matches){
                List<OddsResponse> oddsResponses = MapOddsResponse.convertTOddsResponse(m.getOdds());
                response.add(new MatchResponse(m.getHomeTeam(), m.getAwayTeam(), oddsResponses));
            }
            JsonSender.sendJson(exchange, 200, response, objectMapper);
        } catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server error", objectMapper);
        }
    }

    private void getMatchById(HttpExchange exchange) throws IOException{
        try{
            String path = exchange.getRequestURI().getPath();
            int id = Integer.parseInt(path.substring("/matches/".length()));
            if(id <= 0){
                JsonSender.sendJson(exchange, 400, "Invalid id", objectMapper);
                return;
            }

            Match match = service.getMatchById(id);
            if(match == null){
                JsonSender.sendJson(exchange, 404, "Match " + id + " not found", objectMapper);
                return;
            }
            List<OddsResponse> oddsResponses = MapOddsResponse.convertTOddsResponse(match.getOdds());
            
            MatchResponse response = new MatchResponse(match.getHomeTeam(), match.getAwayTeam(), oddsResponses);
            JsonSender.sendJson(exchange, 200, response, objectMapper);

        }catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server eroor", objectMapper);
        }
        
    }

    private void getMatchesByTeam(HttpExchange exchange) throws IOException{
        try{
            MatchesByTeamRequest request = 
                objectMapper.readValue(
                    exchange.getRequestBody(), MatchesByTeamRequest.class);
            
            if(request == null || !request.isValid()){
                JsonSender.sendJson(exchange, 400, "Invalid request : team", objectMapper);
                return;
            }

            List<Match> matches = service.getMatchesByTeam(request.getTeam());
            List<MatchResponse> response = new ArrayList<>();
            for(Match m : matches){
                List<OddsResponse> oddsResponses = MapOddsResponse.convertTOddsResponse(m.getOdds());
                response.add(new MatchResponse(m.getHomeTeam(), m.getAwayTeam(), oddsResponses));
            }
            JsonSender.sendJson(exchange, 200, response, objectMapper);
        } catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server error", objectMapper);
        }
    }

    private void getOddsByMatchId(HttpExchange exchange) throws IOException{
        try{
            String path = exchange.getRequestURI().getPath();
            int id = Integer.parseInt(path.substring("/matches/".length()));
            if(id <= 0){
                JsonSender.sendJson(exchange, 400, "Invalid id", objectMapper);
                return;
            }

            List<Odd> odds = service.getOddsByMatchId(id);
            if(odds == null){
                JsonSender.sendJson(exchange, 404, "Any odds for the match + " + id, objectMapper);
                return;
            }
            List<OddsResponse> response = new ArrayList<>();
            for(Odd o : odds){
                response.add(new OddsResponse(o.getDescription(), o.getValue()));
            }
            JsonSender.sendJson(exchange, 200, response, objectMapper);

        }catch(Exception e){
            e.printStackTrace();
            JsonSender.sendJson(exchange, 500, "Internal server eroor", objectMapper);
        }
    }
}