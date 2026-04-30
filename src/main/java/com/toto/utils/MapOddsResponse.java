package com.toto.utils;

import java.util.ArrayList;
import java.util.List;

import com.toto.domain.Odd;
import com.toto.dtos.OddsResponse;;

public class MapOddsResponse {
    
    public static List<OddsResponse> convertTOddsResponse(List<Odd> odds){
        List<OddsResponse> oddsResponses = new ArrayList<>();
        for(Odd o : odds){
            oddsResponses.add(new OddsResponse(o.getDescription(), o.getValue()));
        }
        return oddsResponses;     
    }
}
