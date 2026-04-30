package com.toto.service;

import java.util.List;

import com.toto.domain.Match;
import com.toto.domain.Odd;
import com.toto.domain.Sport;
import com.toto.exceptions.MatchNotFoundException;
import com.toto.repository.IMatchRepository;

public class MatchService implements IMatchService{

    private IMatchRepository repo;

    public MatchService(IMatchRepository repo){
        this.repo = repo;
    }
    
    public Match getMatchById(int id){
        Match match = repo.findById(id);
        if(match == null){
            throw new MatchNotFoundException("Match not found with id: " + id);
        }
        return match;
    }

    public List<Match> getMatchesByTeam(String team){
        return repo.findByTeam(team);
    }

    public List<Odd> getOddsByMatchId(int id){
        Match match = repo.findById(id);
        if(match == null){
            throw new MatchNotFoundException("Match not found with id: " + id);
        }
        return match.getOdds();
    }

    public List<Match> getMatchesBySport(Sport sport){
        return repo.findBySport(sport);
    }
}
