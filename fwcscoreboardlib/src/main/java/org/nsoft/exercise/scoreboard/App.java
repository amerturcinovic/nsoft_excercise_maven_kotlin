package org.nsoft.exercise.scoreboard;

import org.nsoft.exercise.scoreboard.impl.FootballWorldCupScoreBoard;
import org.nsoft.exercise.scoreboard.storage.SimpleInsertOrderedInMemoryCollection;

public class App
{
    public static void main( String[] args )
    {
        FootballWorldCupScoreBoard footballWorldCupScoreBoard = new FootballWorldCupScoreBoard();
        FootballWorldCupScoreBoard footballWorldCupScoreBoard1 = new FootballWorldCupScoreBoard(new SimpleInsertOrderedInMemoryCollection());
        System.out.println( "Hello World!" );
    }
}
