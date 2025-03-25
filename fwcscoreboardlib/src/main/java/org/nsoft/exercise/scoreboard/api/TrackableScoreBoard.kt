package org.nsoft.exercise.scoreboard.api

interface TrackableScoreBoard {
    /**
     *
     * This method start new match and add it to score board. This method is idempotent
     *
     * @param homeTeamName  - non-nullable and not empty name of home team as String
     * @param guestTeamName - non-nullable and not empty name of guest team as String
     * @return MatchInfo - return the match information with scores for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo - check model MatchInfo and what information it has
     */
    @Throws(IllegalArgumentException::class)
    fun startMatch(homeTeamName: String, guestTeamName: String): MatchInfo

    /**
     *
     * This method finish match in progress and remove it from score board.
     *
     * @param homeTeamName  - non-nullable and not empty name of home team as String
     * @param guestTeamName - non-nullable and not empty name of guest team as String
     * @return MatchInfo - return the match information with score for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo - check model MatchInfo and what information it has
     */
    @Throws(IllegalArgumentException::class)
    fun finishMatch(homeTeamName: String, guestTeamName: String): MatchInfo

    /**
     *
     * This method update match in progress with new absolute scores for teams. This method is idempotent
     *
     * @param matchInfo - information model MatchInfo for match update name for teams and new score for teams
     * example: { homeTeam: "BRAZIL", homeTeamScore: 1, guestTeamName: "ARGENTINA", 0 }
     * @return MatchInfo - return the match information with score for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo convertToLowerCase
     */
    @Throws(IllegalArgumentException::class)
    fun updateMatch(matchInfo: MatchInfo): MatchInfo

    /**
     *
     * This method return ranking of matches in progress, or empty list if there is no match in progress
     *
     * @return List<MatchInfo></MatchInfo> - return the match information with score for home team and guest team
     */
    fun getBoardRanking(): List<MatchInfo>
}

data class MatchInfo(val homeTeamName: String, val homeTeamScore: Int, val guestTeamName: String, val guestTeamScore: Int)