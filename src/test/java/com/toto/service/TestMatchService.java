package com.toto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.toto.domain.Match;
import com.toto.domain.Sport;
import com.toto.repository.MatchRepository;

public class TestMatchService {
    @Test
    void shouldGetMatchesByTeam(){
        MatchRepository repo = new MatchRepository();
        MatchService service = new MatchService(repo);
        List<Match> macthes = service.getMatchesByTeam("PSG");
        assertEquals(macthes.size(), 1);
    }

    @Test
    void shouldNotGetMatchesByTeam(){
        MatchRepository repo = new MatchRepository();
        MatchService service = new MatchService(repo);
        List<Match> macthes = service.getMatchesByTeam("OL");
        assertEquals(macthes.size(), 0);
    }

    @Test
    void shouldGetMatchesBySport(){
        MatchRepository repo = new MatchRepository();
        MatchService service = new MatchService(repo);
        List<Match> macthes = service.getMatchesBySport(Sport.FOOTBALL);
        assertEquals(macthes.size(), 3);
    }
}
