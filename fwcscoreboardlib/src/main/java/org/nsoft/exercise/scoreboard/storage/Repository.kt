package org.nsoft.exercise.scoreboard.storage

import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.mappers.toMatchInfo
import java.util.Objects

interface Repository {
    fun findByNames(homeTeamName: String, guestTeamName: String): MatchInfo?
    fun save(matchDetailsEntity: MatchDetailsEntity): MatchInfo
    fun delete(matchDetailsEntity: MatchDetailsEntity): MatchInfo
    fun update(matchDetailsEntity: MatchDetailsEntity): MatchInfo
    fun all(): List<MatchInfo>
}

class SimpleInsertOrderedInMemoryCollection : Repository {
    private val storage = LinkedHashMap<Int, MatchDetailsEntity>()

    override fun findByNames(homeTeamName: String, guestTeamName: String) =
        storage[toId(homeTeamName, guestTeamName)]?.toMatchInfo()

    override fun save(matchDetailsEntity: MatchDetailsEntity): MatchInfo {
        val matchId: Int = matchDetailsEntity.hashCode()
        storage[matchId]?.let { throw IllegalArgumentException("Duplicate entry violation exception") }

        storage.putIfAbsent(matchId, matchDetailsEntity)
        return storage[matchId]!!.toMatchInfo()
    }

    override fun delete(matchDetailsEntity: MatchDetailsEntity): MatchInfo {
        val matchId: Int = matchDetailsEntity.hashCode()
        storage[matchId] ?: throw IllegalArgumentException("Entry is not present exception")

        storage.remove(matchId)
        return matchDetailsEntity.toMatchInfo()
    }

    override fun update(matchDetailsEntity: MatchDetailsEntity): MatchInfo {
        val matchId = matchDetailsEntity.hashCode()
        storage[matchId] ?: throw IllegalArgumentException("Entry is not present exception")

        storage[matchId] = matchDetailsEntity
        return matchDetailsEntity.toMatchInfo()
    }

    override fun all(): List<MatchInfo> = storage.values.toList().asReversed().map { it.toMatchInfo() }.toList()

    private fun toId(homeTeamName: String, guestTeamName: String) =
        MatchDetailsEntity(
            homeTeamName = homeTeamName,
            guestTeamName = guestTeamName
        ).hashCode()
}

data class MatchDetailsEntity(
    val homeTeamName: String,
    val guestTeamName: String,
    val homeTeamScore: Int = 0,
    val guestTeamScore: Int = 0
) : Comparable<MatchDetailsEntity> {
    override fun compareTo(other: MatchDetailsEntity) =
        (other.homeTeamScore + other.guestTeamScore) - (homeTeamScore + guestTeamScore)

    override fun hashCode(): Int {

        return Objects.hashCode(homeTeamName + guestTeamName)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatchDetailsEntity

        if (homeTeamName != other.homeTeamName) return false
        if (guestTeamName != other.guestTeamName) return false
        if (homeTeamScore != other.homeTeamScore) return false
        if (guestTeamScore != other.guestTeamScore) return false

        return true
    }
}
