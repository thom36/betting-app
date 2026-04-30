package com.toto.dtos;

import com.toto.domain.Sport;

public class MatchesBySportRequest {
    private Sport sport;

    public Sport getSport(){
        return sport;
    }

    public void setSport(Sport sport){
        this.sport = sport;
    }

    public boolean isValid(){
        return sport != null && !Sport.getAllSports().contains(sport);
    }
}
