package com.toto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.toto.domain.Match;
import com.toto.domain.Odd;
import com.toto.domain.Sport;
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

    private void getMatchesBySport(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query == null || query.isBlank()) {
                JsonSender.sendJson(exchange, 400, "Missing query param: sport", objectMapper);
                return;
            }

            String sportValue = null;

            for (String param : query.split("&")) {
                String[] keyValue = param.split("=", 2);

                if (keyValue.length == 2 && keyValue[0].equals("sport")) {
                    sportValue = keyValue[1];
                    break;
                }
            }

            if (sportValue == null || sportValue.isBlank()) {
                JsonSender.sendJson(exchange, 400, "Missing query param: sport", objectMapper);
                return;
            }

            MatchesBySportRequest request = new MatchesBySportRequest();

            try {
                Sport sport = Sport.valueOf(sportValue.toUpperCase());
                request.setSport(sport);
            } catch (IllegalArgumentException e) {
                JsonSender.sendJson(exchange, 400, "Invalid sport", objectMapper);
                return;
            }

            if (!request.isValid()) {
                JsonSender.sendJson(exchange, 400, "Invalid sport", objectMapper);
                return;
            }

            List<Match> matches = service.getMatchesBySport(request.getSport());

            List<MatchResponse> response = new ArrayList<>();

            for (Match m : matches) {
                List<OddsResponse> oddsResponses =
                        MapOddsResponse.convertTOddsResponse(m.getOdds());

                response.add(
                        new MatchResponse(
                                m.getHomeTeam(),
                                m.getAwayTeam(),
                                oddsResponses
                        )
                );
            }

            JsonSender.sendJson(exchange, 200, response, objectMapper);

        } catch (Exception e) {
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
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query == null || query.isBlank()) {
                JsonSender.sendJson(exchange, 400, "Missing query param: team", objectMapper);
                return;
            }

            String team = null;

            for (String param : query.split("&")) {
                String[] keyValue = param.split("=", 2);

                if (keyValue.length == 2 && keyValue[0].equals("team")) {
                    team = keyValue[1];
                    break;
                }
            }

            MatchesByTeamRequest request = new MatchesByTeamRequest();
            request.setTeam(team);

            if (!request.isValid()) {
                JsonSender.sendJson(exchange, 400, "Invalid team", objectMapper);
                return;
            }

            List<Match> matches = service.getMatchesByTeam(request.getTeam());

            List<MatchResponse> response = new ArrayList<>();

            for (Match m : matches) {
                List<OddsResponse> oddsResponses =
                        MapOddsResponse.convertTOddsResponse(m.getOdds());

                response.add(
                        new MatchResponse(
                                m.getHomeTeam(),
                                m.getAwayTeam(),
                                oddsResponses
                        )
                );
            }

            JsonSender.sendJson(exchange, 200, response, objectMapper);

        } catch (Exception e) {
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