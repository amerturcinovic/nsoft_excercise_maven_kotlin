package org.nsoft.exercise.scoreboard.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nsoft.exercise.scoreboard.api.MatchInfo
import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard

class FootballWorldCupScoreBoardTest {
    private lateinit var footballScoreBoard: TrackableScoreBoard

    @BeforeEach
    fun init() {
        footballScoreBoard = FootballWorldCupScoreBoard()
    }

    @Test
    fun `when new match start expect to show on Score board`() {
        // given
        val expectedMatchInfo = MatchInfo("BRAZIL", 0, "ARGENTINA", 0)

        // when
        val actualMatchInfo: MatchInfo = footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")

        // then
        assertEquals(expectedMatchInfo, actualMatchInfo)
        assertEquals(listOf(expectedMatchInfo), footballScoreBoard.getBoardRanking())
    }

    @Test
    fun `when match already started and finish called expect empty score board`() {
        // given
        val expectedEmptyScoreBoard = listOf<MatchInfo>()

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")
        footballScoreBoard.finishMatch("BRAZIL", "ARGENTINA")

        // then
        assertEquals(expectedEmptyScoreBoard, footballScoreBoard.getBoardRanking())
    }

    @Test
    fun `when match already started and updated called expect to show on score board`() {
        // given
        val expectedMathInfoUpdate = MatchInfo("BRAZIL", 1, "ARGENTINA", 1)

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")
        val actualMatchInfoUpdate = footballScoreBoard.updateMatch(expectedMathInfoUpdate)

        // then
        assertEquals(actualMatchInfoUpdate, expectedMathInfoUpdate)
        assertEquals(listOf(expectedMathInfoUpdate), footballScoreBoard.getBoardRanking())
    }

    @Test
    fun `when multiple natch started expect to show on score board sorted by most recent start`() {
        // given
        val expectedMatchesInProgress = listOf(
            MatchInfo("CROATIA", 0, "ENGLAND", 0),
            MatchInfo("SPAIN", 0, "FRANCE", 0),
            MatchInfo("BRAZIL", 0, "ARGENTINA", 0)
        )

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")
        footballScoreBoard.startMatch("SPAIN", "FRANCE")
        footballScoreBoard.startMatch("CROATIA", "ENGLAND")

        //then
        assertEquals(expectedMatchesInProgress, footballScoreBoard.getBoardRanking())
    }

    @Test
    fun `when multiple match started and updated score expect to show on score board sorted by most scores and most recent start`() {
        // given
        val expectedMatchesInProgress = listOf(
            MatchInfo("SPAIN", 1, "FRANCE", 1),
            MatchInfo("BRAZIL", 1, "ARGENTINA", 0),
            MatchInfo("PORTUGAL", 0, "ITALY", 0),
            MatchInfo("CROATIA", 0, "ENGLAND", 0)
        )

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")
        footballScoreBoard.startMatch("SPAIN", "FRANCE")
        footballScoreBoard.startMatch("CROATIA", "ENGLAND")
        footballScoreBoard.startMatch("PORTUGAL", "ITALY")

        footballScoreBoard.updateMatch(
            MatchInfo("BRAZIL", 1, "ARGENTINA", 0)
        )
        footballScoreBoard.updateMatch(
            MatchInfo("SPAIN", 1, "FRANCE", 0)
        )
        footballScoreBoard.updateMatch(
            MatchInfo("SPAIN", 1, "FRANCE", 1)
        )

        // then
        assertEquals(
            """
                1. SPAIN 1 - FRANCE 1
                2. BRAZIL 1 - ARGENTINA 0
                3. PORTUGAL 0 - ITALY 0
                4. CROATIA 0 - ENGLAND 0
                """.trimIndent(), footballScoreBoard.toString()
        )
        assertEquals(expectedMatchesInProgress, footballScoreBoard.getBoardRanking())
    }

    @Test
    fun `when match already started and called start again expect exception`() {
        // given
        val expectedMatchInfo = MatchInfo("BRAZIL", 0, "FRANCE", 0)

        // when
        footballScoreBoard.startMatch("BRAZIL", "FRANCE")

        // then
        assertEquals(listOf(expectedMatchInfo), footballScoreBoard.getBoardRanking())
        assertThrows(
            IllegalArgumentException::class.java
        ) { footballScoreBoard.startMatch("BRAZIL", "FRANCE") }
    }

    @Test
    fun `when update match called with wrong score expect exception`() {
        // when
        footballScoreBoard.startMatch("BRAZIL", "FRANCE")
        footballScoreBoard.updateMatch(MatchInfo("BRAZIL", 2, "FRANCE", 1))

        // then
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.updateMatch(
                MatchInfo("BRAZIL", 1, "FRANCE", 1)
            )
        }
    }

    @Test
    fun `when finish or update match called when match is not in progress expect exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.finishMatch("BRAZIL", "ARGENTINA")
        }

        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.updateMatch(
                MatchInfo("BRAZIL", 1, "ARGENTINA", 0)
            )
        }
    }

    @Test
    fun `when start match called with wrong arguments expect exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.startMatch("ARGENTINA", "")
        }

        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.startMatch("", "ARGENTINA")
        }
    }

    @Test
    fun `when finish match called with wrong argument expect exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.finishMatch("", "BRAZIL")
        }
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.finishMatch("BRAZIL", "")
        }
    }

    @Test
    fun `when update match called with wrong argument expect exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.updateMatch(
                MatchInfo("", 1, "", 0)
            )
        }
    }
}