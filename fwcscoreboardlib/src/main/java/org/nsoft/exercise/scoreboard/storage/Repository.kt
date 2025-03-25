package org.nsoft.exercise.scoreboard.storage

import org.nsoft.exercise.scoreboard.api.MatchInfo

interface Repository {
    fun findByNames(homeTeamName: String, guestTeamName: String): MatchInfo?
    fun save(matchInfo: MatchInfo): MatchInfo
    fun delete(matchInfo: MatchInfo): MatchInfo
    fun update(matchInfo: MatchInfo): MatchInfo
    val all: List<MatchInfo>
}

class SimpleInsertOrderedInMemoryCollection : Repository {
    private val storage: Map<Int, MatchDetailsEntity> = LinkedHashMap()

    override fun findByNames(homeTeamName: String, guestTeamName: String) =
        storage[toId(homeTeamName, guestTeamName)]?.toMatchInfo()

    override fun save(matchInfo: MatchInfo): MatchInfo {
        TODO("Not yet implemented")
    }

    override fun delete(matchInfo: MatchInfo): MatchInfo {
        TODO("Not yet implemented")
    }

    override fun update(matchInfo: MatchInfo): MatchInfo {
        TODO("Not yet implemented")
    }

    override val all: List<MatchInfo>
        get() = TODO("Not yet implemented")

    private fun toId(homeTeamName: String, guestTeamName: String) =
        MatchDetailsEntity(
            homeTeamName = homeTeamName,
            guestTeamName = guestTeamName
        ).hashCode()
}

data class MatchDetailsEntity(
    val homeTeamName: String,
    val homeTeamScore: Int = 0,
    val guestTeamName: String,
    val guestTeamScore: Int = 0
) : Comparable<MatchDetailsEntity> {
    override fun compareTo(other: MatchDetailsEntity) =
        (other.homeTeamScore + other.guestTeamScore) - (homeTeamScore + guestTeamScore)
}

fun MatchDetailsEntity.toMatchInfo() = MatchInfo(
    this.guestTeamName,
    this.homeTeamScore,
    this.guestTeamName,
    this.guestTeamScore
)

fun MatchInfo.toMatchEntity() = MatchDetailsEntity(
    this.guestTeamName,
    this.homeTeamScore,
    this.guestTeamName,
    this.guestTeamScore
)
