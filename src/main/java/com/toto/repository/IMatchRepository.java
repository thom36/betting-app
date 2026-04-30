package com.toto.repository;
import java.util.List;

import com.toto.domain.Match;
import com.toto.domain.Sport;

public interface IMatchRepository{

    Match findById(int id);
    List<Match> findByTeam(String team);
    List<Match> findBySport(Sport sport);
}