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

        matchScoreModel.statsPlayer1.point = 40
        matchScoreModel.statsPlayer2.point = 15

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(1, matchScoreModel.statsPlayer1.game)
        assertEquals(0, matchScoreModel.statsPlayer2.point)
    }

    @Test
    fun testStartUnderLowerCase() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1.point = 30
        matchScoreModel.statsPlayer2.point = 40

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(40, matchScoreModel.statsPlayer1.point)
        assertEquals(40, matchScoreModel.statsPlayer2.point)
    }

    @Test
    fun testTiebreakStart() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")

        matchScoreModel.statsPlayer1.point = 40
        matchScoreModel.statsPlayer1.game = 5
        matchScoreModel.statsPlayer2.game = 6

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(0, matchScoreModel.statsPlayer1.game)
        assertEquals(0, matchScoreModel.statsPlayer2.game)
        assertEquals(0, matchScoreModel.statsPlayer1.set)
        assertEquals(0, matchScoreModel.statsPlayer2.set)
    }

    @Test
    fun testTiebreakCountPointsProperly() {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val matchScoreModel = MatchScoreModel(Match(), "", "")


        matchScoreModel.statsPlayer1.point = 40
        matchScoreModel.statsPlayer1.game = 5
        matchScoreModel.statsPlayer2.game = 6

        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        matchScoreCalculationService.updateMatchState(matchScoreModel, "player1")
        assertEquals(1, matchScoreModel.statsPlayer1.game)
        assertEquals(0, matchScoreModel.statsPlayer2.game)
        assertEquals(0, matchScoreModel.statsPlayer1.set)
        assertEquals(0, matchScoreModel.statsPlayer2.set)

    }
}