package service

import exception.GameFinishedException
import model.MatchScoreModel
import util.Validation

private const val WINNING_SETS_COUNT = 2


class MatchScoreCalculationService {
    private val validator = Validation()
    private var tiebreakFlag : Boolean = false

    fun updateMatchState(uuid: String, player: String) {
        val matchScore = OngoingMatchesService.getMatch(uuid)
        checkGameFinished(matchScore)
        if (!tiebreakFlag) {
            updateScoreStats(matchScore, player)
        } else {
            updateGames(matchScore, player)
        }
    }


    private fun checkGameFinished(match : MatchScoreModel?) {
        // TODO move validation for wrong uuid into validation class
        if (match == null) {
            throw IllegalArgumentException("Неверный uuid")
        }

        if (match.statsPlayer1["set"] == WINNING_SETS_COUNT) {
            match.match.winner = match.match.player1
            throw GameFinishedException()
        }
        else if (match.statsPlayer2["set"] == WINNING_SETS_COUNT) {
            match.match.winner = match.match.player2
            throw GameFinishedException()
        }
    }

    private fun updateScoreStats(matchScore : MatchScoreModel, player: String) {
        updatePoints(matchScore, player)
    }

    private fun selectPlayerStats(matchScore: MatchScoreModel, scoredPlayer : String) : MutableMap<String, Int> {
        if (scoredPlayer == "player1"){
            return matchScore.statsPlayer1
        }
        return matchScore.statsPlayer2
    }

    private fun isTiebreakState(matchScore: MatchScoreModel) : Boolean{
        val gameCountPlayer1 = matchScore.statsPlayer1["game"]
        val gameCountPlayer2 = matchScore.statsPlayer2["game"]
        return gameCountPlayer1 == 6 && gameCountPlayer2 == 6
    }

    private fun updatePoints(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        when (stats["point"]) {
            0 -> stats["point"] = stats["point"]!! + 15
            15 -> stats["point"] = stats["point"]!! + 15
            30 -> {
                stats["point"] = 0
                updateGames(matchScore, player)
            }
        }
    }

    private fun updateGames(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        stats["game"] = stats["game"]!! + 1

        when {
            !tiebreakFlag && isTiebreakState(matchScore) -> {
                tiebreakFlag = true
                resetGameScores(matchScore)
            }
            stats["game"] == 7 -> {
                tiebreakFlag = false
                saveSetResults(matchScore)
                resetGameScores(matchScore)
                updateSets(matchScore, player)
            }
        }
    }

    private fun resetGameScores(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1["game"] = 0
        matchScore.statsPlayer1["point"] = 0
        matchScore.statsPlayer2["game"] = 0
        matchScore.statsPlayer2["point"] = 0
    }

    private fun saveSetResults(matchScore: MatchScoreModel) {
        matchScore.player1Sets.add(matchScore.statsPlayer1["game"]!!)
        matchScore.player2Sets.add(matchScore.statsPlayer2["game"]!!)
    }

    private fun updateSets(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        stats["set"] = stats["set"]!! + 1
    }
}