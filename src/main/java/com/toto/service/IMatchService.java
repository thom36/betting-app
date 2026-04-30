package com.toto.service;

import java.util.List;

import com.toto.domain.Match;
import com.toto.domain.Odd;
import com.toto.domain.Sport;

public interface IMatchService {
    Match getMatchById(int id);
    List<Match> getMatchesByTeam(String team);
    List<Odd> getOddsByMatchId(int id);
    List<Match> getMatchesBySport(Sport sport);
}
