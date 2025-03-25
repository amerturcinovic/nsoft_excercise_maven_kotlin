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
    fun whenOneMatchStarted_ExpectToShow_OnScoreBoard() {
        // given
        val expectedMatchInfo = MatchInfo("BRAZIL", 0, "ARGENTINA", 0)

        // when
        val actualMatchInfo: MatchInfo = footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")

        // then
        assertEquals(expectedMatchInfo, actualMatchInfo)
        assertEquals(listOf(expectedMatchInfo), footballScoreBoard.getBoardRanking())
    }

    @Test
    fun whenMatchAlreadyStarted_And_FinishedCalled_Expect_EmptyScoreBoard() {
        // given
        val expectedEmptyScoreBoard = listOf<MatchInfo>()

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA")
        footballScoreBoard.finishMatch("BRAZIL", "ARGENTINA")

        // then
        assertEquals(expectedEmptyScoreBoard, footballScoreBoard.getBoardRanking())
    }

    @Test
    fun whenMatchAlreadyStarted_And_UpdatedCalled_ExpectToShow_OnScoreBoard() {
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
    fun whenMultipleMachStarted_ExpectToShow_OnScoreBoard_SortedByMostRecentStart() {
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
    fun whenMultipleMachStarted__And_UpdateScore_ExpectToShow_OnScoreBoard_SortedByMostScores_And_MostRecentStart() {
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
    fun whenMatchAlreadyStarted_AndCalledStartAgain_Expect_Exception() {
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
    fun whenUpdateMatchCalled_WithWrongScore_Expect_Exception() {
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
    fun whenFinish_Or_Update_MatchCalled_WhenMatchIsNotInProgress_Expect_Exception() {
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
    fun whenStartMatchCalled_WithInvalidArgument_Expect_Exception() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.startMatch(
                "BRAZIL",
                ""
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.startMatch(
                "",
                "ARGENTINA"
            )
        }
    }

    @Test
    fun whenFinishMatchCalled_WithInvalidArgument_Expect_Exception() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.finishMatch(
                "",
                "BRAZIL"
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.finishMatch(
                "BRAZIL",
                ""
            )
        }
    }

    @Test
    fun whenUpdateMatchCalled_WithInvalidArgument_Expect_Exception() {
        assertThrows(IllegalArgumentException::class.java) {
            footballScoreBoard.updateMatch(
                MatchInfo("", 1, "", 0)
            )
        }
    }
}