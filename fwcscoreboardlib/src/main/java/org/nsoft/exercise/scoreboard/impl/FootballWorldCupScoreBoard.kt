package org.nsoft.exercise.scoreboard.impl

import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard
import org.nsoft.exercise.scoreboard.storage.Repository
import org.nsoft.exercise.scoreboard.storage.SimpleInsertOrderedInMemoryCollection

class FootballWorldCupScoreBoard(
    val repository: Repository = SimpleInsertOrderedInMemoryCollection()
) : TrackableScoreBoard {
    override fun startMatch(homeTeamName: String, guestTeamName: String): MatchInfo {
        if (homeTeamName.isBlank() || guestTeamName.isBlank())
            throw IllegalArgumentException("You must provide name for both teams")

        return MatchInfo("A", 0, "B", 1)
    }

    override fun finishMatch(homeTeamName: String, guestTeamName: String): MatchInfo {
        return MatchInfo("A", 0, "B", 1)
    }

    override fun updateMatch(matchInfo: MatchInfo): MatchInfo {
        return MatchInfo("A", 0, "B", 1)
    }

    override fun getBoardRanking(): List<MatchInfo> {
        return listOf(MatchInfo("A", 0, "B", 1))
    }
}