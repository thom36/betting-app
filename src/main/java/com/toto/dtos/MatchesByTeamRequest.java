package com.toto.dtos;

public class MatchesByTeamRequest {
    private String team;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isValid() {
        return team != null && !team.trim().isEmpty();
    }
}
