package com.toto.repository;

import java.util.ArrayList;
import java.util.List;

import com.toto.domain.Match;
import com.toto.domain.Odd;
import com.toto.domain.Sport;

public class FakeDB {

    private static volatile FakeDB dbInstance;

    private static final List<Match> matches = new ArrayList<>();

    static {
        Match match1 = new Match(1, "PSG", "OM", Sport.FOOTBALL);
        match1.addOdd(new Odd(1, "Victoire PSG", 1.65f));
        match1.addOdd(new Odd(2, "Match nul", 3.80f));
        match1.addOdd(new Odd(3, "Victoire OM", 4.50f));
        matches.add(match1);

        Match match2 = new Match(2, "Real", "Barcelone", Sport.FOOTBALL);
        match2.addOdd(new Odd(4, "Victoire Real", 2.10f));
        match2.addOdd(new Odd(5, "Match nul", 3.40f));
        match2.addOdd(new Odd(6, "Victoire Barcelone", 2.90f));
        matches.add(match2);

        Match match3 = new Match(3, "Arsenal", "Chelsea", Sport.FOOTBALL);
        match3.addOdd(new Odd(7, "Victoire Arsenal", 1.90f));
        match3.addOdd(new Odd(8, "Match nul", 3.30f));
        match3.addOdd(new Odd(9, "Victoire Chelsea", 3.60f));
        matches.add(match3);

        Match match4 = new Match(4, "UBB", "Stade Toulousain", Sport.RUGBY);
        match4.addOdd(new Odd(10, "Victoire UBB", 2.25f));
        match4.addOdd(new Odd(11, "Match nul", 18.00f));
        match4.addOdd(new Odd(12, "Victoire Stade Toulousain", 1.70f));
        matches.add(match4);

        Match match5 = new Match(5, "Castres", "Racing92", Sport.RUGBY);
        match5.addOdd(new Odd(13, "Victoire Castres", 1.95f));
        match5.addOdd(new Odd(14, "Match nul", 17.00f));
        match5.addOdd(new Odd(15, "Victoire Racing92", 2.05f));
        matches.add(match5);

        Match match6 = new Match(6, "Toulon", "Stade Français", Sport.RUGBY);
        match6.addOdd(new Odd(16, "Victoire Toulon", 1.75f));
        match6.addOdd(new Odd(17, "Match nul", 19.00f));
        match6.addOdd(new Odd(18, "Victoire Stade Français", 2.35f));
        matches.add(match6);

        Match match7 = new Match(7, "OKC", "Denver", Sport.BASKETBALL);
        match7.addOdd(new Odd(19, "Victoire OKC", 1.85f));
        match7.addOdd(new Odd(20, "Victoire Denver", 1.95f));
        matches.add(match7);

        Match match8 = new Match(8, "Cavs", "Celtics", Sport.BASKETBALL);
        match8.addOdd(new Odd(21, "Victoire Cavs", 2.20f));
        match8.addOdd(new Odd(22, "Victoire Celtics", 1.70f));
        matches.add(match8);

        Match match9 = new Match(9, "Warriors", "Suns", Sport.BASKETBALL);
        match9.addOdd(new Odd(23, "Victoire Warriors", 1.95f));
        match9.addOdd(new Odd(24, "Victoire Suns", 1.85f));
        matches.add(match9);

        Match match10 = new Match(10, "Sinner", "Alcaraz", Sport.TENNIS);
        match10.addOdd(new Odd(25, "Victoire Sinner", 1.90f));
        match10.addOdd(new Odd(26, "Victoire Alcaraz", 1.90f));
        matches.add(match10);

        Match match11 = new Match(11, "Tsonga", "Wawrinka", Sport.TENNIS);
        match11.addOdd(new Odd(27, "Victoire Tsonga", 2.60f));
        match11.addOdd(new Odd(28, "Victoire Wawrinka", 1.50f));
        matches.add(match11);

        Match match12 = new Match(12, "Djokovic", "Nadal", Sport.TENNIS);
        match12.addOdd(new Odd(29, "Victoire Djokovic", 1.75f));
        match12.addOdd(new Odd(30, "Victoire Nadal", 2.10f));
        matches.add(match12);
    }

    private FakeDB() {
    }

    public static FakeDB getInstance() {
        if (dbInstance == null) {
            synchronized (FakeDB.class) {
                if (dbInstance == null) {
                    dbInstance = new FakeDB();
                }
            }
        }
        return dbInstance;
    }

    public List<Match> getMatches() {
        return matches;
    }
}