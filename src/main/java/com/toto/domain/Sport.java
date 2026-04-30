package com.toto.domain;

import java.util.Set;

public enum Sport{
    FOOTBALL,
    BASKETBALL,
    TENNIS,
    RUGBY;

    public static Set<Sport> getAllSports(){
        return Set.of(FOOTBALL, BASKETBALL, RUGBY, TENNIS);
    }
}
