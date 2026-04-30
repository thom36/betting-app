package com.toto.repository;

import java.util.List;

import com.toto.domain.Match;
import com.toto.domain.Sport;

public class MatchRepository implements IMatchRepository{

    private FakeDB db;

    public MatchRepository(){
        this.db = FakeDB.getInstance();
    }
    public Match findById(int id){
        return db.getMatches().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Match> findByTeam(String team){
        return db.getMatches().stream()
                .filter(m -> m.getHomeTeam() == team || m.getAwayTeam() == team)
                .toList();
    }

    public List<Match> findBySport(Sport sport){
        return db.getMatches().stream()
                .filter(m -> m.getSport() == sport)
                .toList();
    }
}
