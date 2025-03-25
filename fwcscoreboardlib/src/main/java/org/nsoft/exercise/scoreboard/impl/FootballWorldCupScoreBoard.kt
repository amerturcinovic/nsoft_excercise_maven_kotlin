package org.nsoft.exercise.scoreboard.impl

import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard
import org.nsoft.exercise.scoreboard.mappers.toMatchEntity
import org.nsoft.exercise.scoreboard.storage.MatchDetailsEntity
import org.nsoft.exercise.scoreboard.storage.Repository
import org.nsoft.exercise.scoreboard.storage.SimpleInsertOrderedInMemoryCollection

class FootballWorldCupScoreBoard(
    private val repository: Repository = SimpleInsertOrderedInMemoryCollection()
) : TrackableScoreBoard {
    override fun startMatch(homeTeamName: String, guestTeamName: String) =
        validateArguments(homeTeamName, guestTeamName).let {
            repository.save(MatchDetailsEntity(homeTeamName, guestTeamName))
        }

    override fun finishMatch(homeTeamName: String, guestTeamName: String) =
        repository.findByNames(homeTeamName, guestTeamName)?.let {
            validateArguments(homeTeamName, guestTeamName)
            repository.delete(MatchDetailsEntity(homeTeamName, guestTeamName))
        } ?: throw IllegalArgumentException("Match is not in progress")

    override fun updateMatch(matchInfo: MatchInfo) =
        repository.findByNames(matchInfo.homeTeamName, matchInfo.guestTeamName)?.let { matchInProgress ->
            validateArguments(matchInfo.homeTeamName, matchInfo.guestTeamName)
            validateScoreValues(matchInfo, matchInProgress)
            repository.update(matchInfo.toMatchEntity())
        } ?: throw IllegalArgumentException("Match is not in progress")

    override fun toString() = getSorted().let {
        buildString {
            it.forEachIndexed { index, matchInfo -> appendLine("${index + 1}. $matchInfo") }
        }.removeSuffix("\n")
    }


    private fun getSorted(): List<MatchInfo> = repository.all().stream().sorted().toList()

    override fun getBoardRanking() = getSorted()

    private fun validateArguments(homeTeamName: String, guestTeamName: String) =
        require((homeTeamName.isEmpty() || guestTeamName.isEmpty()).not()) {
            throw IllegalArgumentException("You must provide name for both teams")
        }

    private fun validateScoreValues(matchInfo: MatchInfo, mathInfoInProgress: MatchInfo) {
        val scoreIsLessThanCurrent = mathInfoInProgress.homeTeamScore < matchInfo.homeTeamScore ||
                mathInfoInProgress.guestTeamScore < matchInfo.guestTeamScore
        require(scoreIsLessThanCurrent) {
            throw IllegalArgumentException("Match score must be positive number an not less than current score")
        }
    }
}