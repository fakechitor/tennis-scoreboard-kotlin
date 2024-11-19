package score_calculation

import model.Match
import model.MatchScoreModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import service.MatchScoreCalculationService

class MatchScoreCalculationTest {

    @Test
    fun testUpdateGamePoints() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1 = mutableMapOf(
            "point" to 40,
            "game" to 0
        )
        matchScoreModel.statsPlayer2 = mutableMapOf(
            "point" to 0
        )

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(1, matchScoreModel.statsPlayer1["game"])
    }

    @Test
    fun testStartUnderLowerCase() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1 = mutableMapOf(
            "point" to 30,
        )
        matchScoreModel.statsPlayer2 = mutableMapOf(
            "point" to 40,
        )

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(40, matchScoreModel.statsPlayer1["point"])
    }

    @Test
    fun testTiebreakStart() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1 = mutableMapOf(
            "point" to 40,
            "game" to 5,
            "set" to 0,
        )
        matchScoreModel.statsPlayer2 = mutableMapOf(
            "point" to 0,
            "game" to 6,
            "set" to 0,
        )

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(0, matchScoreModel.statsPlayer1["game"])
    }

    @Test
    fun testTiebreakCountPointsProperly() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1 = mutableMapOf(
            "point" to 40,
            "game" to 5,
            "set" to 0,
        )
        matchScoreModel.statsPlayer2 = mutableMapOf(
            "point" to 0,
            "game" to 6,
            "set" to 0,
        )

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(1, matchScoreModel.statsPlayer1["game"])
    }
}