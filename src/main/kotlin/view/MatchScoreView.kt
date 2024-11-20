package view

import constant.CurrentScoreKeys
import constant.GameState
import model.MatchScoreModel

class MatchScoreView {

    fun getMatchScoreDataForView(matchScore : MatchScoreModel, state : GameState) : Map<String, Any> {
        return when(state) {
            GameState.NORMAL -> prepareMatchScore(matchScore)
            GameState.UNDER_LOWER -> prepareMatchScoreWithUnderLower(matchScore)
            GameState.TIEBREAK -> prepareMatchScore(matchScore)
            GameState.FINISHED ->prepareMatchScoreFinished(matchScore)
        }

    }

    private fun prepareMatchScore(match: MatchScoreModel) : Map<String, Int> {
        val matchData = mutableMapOf<String, Int>()

        matchData[CurrentScoreKeys.PLAYER_1_POINTS] = match.statsPlayer1["point"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_GAMES] = match.statsPlayer1["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_SETS] = match.statsPlayer1["set"] ?: 0

        matchData[CurrentScoreKeys.PLAYER_2_POINTS] = match.statsPlayer2["point"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_GAMES] = match.statsPlayer2["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_SETS] = match.statsPlayer2["set"] ?: 0

        return matchData
    }

    private fun prepareMatchScoreWithUnderLower(match: MatchScoreModel) : Map<String, Any> {
        val matchData = mutableMapOf<String, Any>()

        matchData[CurrentScoreKeys.PLAYER_1_POINTS] =  if (match.statsPlayer1["advantage"] == 1) "+" else "-"
        matchData[CurrentScoreKeys.PLAYER_1_GAMES] = match.statsPlayer1["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_SETS] = match.statsPlayer1["set"] ?: 0

        matchData[CurrentScoreKeys.PLAYER_2_POINTS] =  if (match.statsPlayer2["advantage"] == 1) "+" else "-"
        matchData[CurrentScoreKeys.PLAYER_2_GAMES] = match.statsPlayer2["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_SETS] = match.statsPlayer2["set"] ?: 0

        return matchData
    }

    private fun prepareMatchScoreFinished(match: MatchScoreModel) : Map<String, Any> {
        val matchData = mutableMapOf<String, Any>()
        matchData[CurrentScoreKeys.PLAYER_1_POINTS] = match.player1Sets[0]
        matchData[CurrentScoreKeys.PLAYER_1_GAMES] = match.player1Sets[1]
        matchData[CurrentScoreKeys.PLAYER_1_SETS] = match.player1Sets[2]

        matchData[CurrentScoreKeys.PLAYER_2_POINTS] = match.player2Sets[0]
        matchData[CurrentScoreKeys.PLAYER_2_GAMES] = match.player2Sets[1]
        matchData[CurrentScoreKeys.PLAYER_2_SETS] = match.player2Sets[2]


        return matchData
    }

    fun getColumnNames(state: GameState) : List<String> {
        return when(state) {
            GameState.NORMAL -> listOf("Сеты", "Геймы", "Очки")
            GameState.TIEBREAK -> listOf("Сеты", "Тайбрейк", "Очки")
            GameState.UNDER_LOWER -> listOf("Сеты", "Геймы", "Преимущество")
            GameState.FINISHED -> listOf("Сеты", "Cет 1", "Cет 2", "Cет 3")
        }
    }

    fun getNamesList(match: MatchScoreModel): List<String> {
        return listOf(match.namePlayer1, match.namePlayer2)
    }
}