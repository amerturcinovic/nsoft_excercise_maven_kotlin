package org.nsoft.exercise.scoreboard.mappers

import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.storage.MatchDetailsEntity

fun MatchDetailsEntity.toMatchInfo() = MatchInfo(
    this.homeTeamName,
    this.homeTeamScore,
    this.guestTeamName,
    this.guestTeamScore
)

fun MatchInfo.toMatchEntity() = MatchDetailsEntity(
    this.homeTeamName,
    this.guestTeamName,
    this.homeTeamScore,
    this.guestTeamScore
)