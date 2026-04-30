package com.toto.domain;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private int id;
    private String homeTeam;
    private String awayTeam;
    private Sport sport;
    private List<Odd> odds;

    public Match(int matchId, String homeTeam, String awayTeam, Sport sport){
        this.id = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.sport = sport;
        odds = new ArrayList<>();
    }

    public void addOdd(Odd odd){
        this.odds.add(odd);
    }

    public int getId(){
        return id;
    }

    public String getAwayTeam(){
        return awayTeam;
    }

    public String getHomeTeam(){
        return homeTeam;
    }

    public Sport getSport(){
        return sport;
    }

    public List<Odd> getOdds(){
        return odds;
    }
}
