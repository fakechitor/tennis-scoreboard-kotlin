package view

import constant.CurrentScoreKeys
import constant.GameState
import model.MatchScoreModel

class MatchScoreView {

    fun prepareMatchScoreForView(match: MatchScoreModel) : Map<String, Int> {
        val matchData = mutableMapOf<String, Int>()

        matchData[CurrentScoreKeys.PLAYER_1_POINTS] = match.statsPlayer1["point"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_GAMES] = match.statsPlayer1["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_SETS] = match.statsPlayer1["set"] ?: 0

        matchData[CurrentScoreKeys.PLAYER_2_POINTS] = match.statsPlayer2["point"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_GAMES] = match.statsPlayer2["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_SETS] = match.statsPlayer2["set"] ?: 0

        return matchData
    }

    fun prepareMatchScoreForView2(match: MatchScoreModel) : Map<String, Any> {
        val matchData = mutableMapOf<String, Any>()

        matchData[CurrentScoreKeys.PLAYER_1_POINTS] =  if (match.statsPlayer1["advantage"] == 1) "+" else "-"
        matchData[CurrentScoreKeys.PLAYER_1_GAMES] = match.statsPlayer1["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_1_SETS] = match.statsPlayer1["set"] ?: 0

        matchData[CurrentScoreKeys.PLAYER_2_POINTS] =  if (match.statsPlayer2["advantage"] == 1) "+" else "-"
        matchData[CurrentScoreKeys.PLAYER_2_GAMES] = match.statsPlayer2["game"] ?: 0
        matchData[CurrentScoreKeys.PLAYER_2_SETS] = match.statsPlayer2["set"] ?: 0

        return matchData
    }

    fun getColumnNames(state: GameState) : List<String> {
        return when(state) {
            GameState.NORMAL -> listOf("Сеты", "Геймы", "Очки")
            GameState.TIEBREAK -> listOf("Сеты", "Тайбрейк", "Очки")
            GameState.UNDER_LOWER -> listOf("Сеты", "Геймы", "Преимущество")
        }
    }

    fun getNamesList(match: MatchScoreModel): List<String> {
        return listOf(match.namePlayer1, match.namePlayer2)
    }
}