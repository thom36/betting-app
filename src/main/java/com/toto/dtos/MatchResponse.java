package com.toto.dtos;

import java.util.List;

public class MatchResponse {
    public String homeTeam;
    public String awayTeam;
    public List<OddsResponse> odds;

    public MatchResponse(String homeTeam, String awayTeam, List<OddsResponse> odds) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.odds = odds;
    }
}
