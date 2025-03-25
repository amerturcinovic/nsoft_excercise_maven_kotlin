package org.nsoft.exercise.scoreboard.example

import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.impl.FootballWorldCupScoreBoard

class App {
    private var scoreBoard = FootballWorldCupScoreBoard()

    fun main() {
        scoreBoard.startMatch("ARGENTINA", "BRAZIL")
        scoreBoard.startMatch("FRANCE", "SPAIN")
        scoreBoard.startMatch("PORTUGAL", "DENMARK")
        scoreBoard.startMatch("ENGLAND", "GERMANY")

        var boardRanking: List<MatchInfo> = scoreBoard.getBoardRanking()

        // This should return list of matches from newest to oldest started
        println(boardRanking)
        println()
        println(scoreBoard)
        // This should print also matches from newest to oldest started
        /*
                1. ENGLAND 0 - GERMANY 0
                2. PORTUGAL 0 - DENMARK 0
                3. FRANCE 0 - SPAIN 0
                4. ARGENTINA 0 - BRAZIL 0
             */

        // Update matches
        scoreBoard.updateMatch(MatchInfo("ARGENTINA", 0, "BRAZIL", 1))
        scoreBoard.updateMatch(MatchInfo("FRANCE", 0, "SPAIN", 1))
        scoreBoard.updateMatch(MatchInfo("FRANCE", 1, "SPAIN", 1))
        scoreBoard.updateMatch(MatchInfo("ARGENTINA", 1, "BRAZIL", 1))

        boardRanking = scoreBoard.getBoardRanking()


        // This should return list as:
        // [
        // MatchInfo[homeTeamName=FRANCE,homeTeamScore=1, guestTeamName=SPAIN, guestTeamScore=1],
        // MatchInfo[homeTeamName=ARGENTINA, homeTeamScore=1, guestTeamName=BRAZIL, guestTeamScore=1],
        // MatchInfo[homeTeamName=ENGLAND, homeTeamScore=0, guestTeamName=GERMANY, guestTeamScore=0],
        // MatchInfo[homeTeamName=PORTUGAL, homeTeamScore=0, guestTeamName=DENMARK, guestTeamScore=0]
        // ]
        println()
        println(boardRanking)

        println()
        println(scoreBoard)
        // This should print matches with most scores and for matches with same scores most recent started first
        /*
            1. FRANCE 1 - SPAIN 1
            2. ARGENTINA 1 - BRAZIL 1
            3. ENGLAND 0 - GERMANY 0
            4. PORTUGAL 0 - DENMARK 0
            */

        // Finish one match
        scoreBoard.finishMatch("ARGENTINA", "BRAZIL")

        boardRanking = scoreBoard.getBoardRanking()


        // This should return list as:
        // [
        // MatchInfo[homeTeamName=FRANCE,homeTeamScore=1, guestTeamName=SPAIN, guestTeamScore=1],
        // MatchInfo[homeTeamName=ENGLAND, homeTeamScore=0, guestTeamName=GERMANY, guestTeamScore=0],
        // MatchInfo[homeTeamName=PORTUGAL, homeTeamScore=0, guestTeamName=DENMARK, guestTeamScore=0]
        // ]
        println()
        println(boardRanking)

        println()
        println(scoreBoard)
    }
}