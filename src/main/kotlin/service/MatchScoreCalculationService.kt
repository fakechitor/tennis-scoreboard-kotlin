package service

import exception.GameFinishedException
import model.MatchScoreModel

private const val WINNING_SETS_COUNT = 2
private const val HAVE_ADVANTAGE = 1
private const val NOT_HAVE_ADVANTAGE = 0


class MatchScoreCalculationService {
    private var tiebreakFlag : Boolean = false
    private var underLowerFlag : Boolean = false

    fun updateMatchState(matchScore: MatchScoreModel, player: String) {
        if (tiebreakFlag) {
            updateGames(matchScore, player)
        }
        else if (underLowerFlag) {
            handleAdvantageCase(matchScore, player)
        }
        else {
            updateScoreStats(matchScore, player)
        }
    }

    private fun handleAdvantageCase(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        if (stats["advantage"] == HAVE_ADVANTAGE) {
            resetAdvantage(matchScore)
            resetPointScores(matchScore)
            updateGames(matchScore, player)
            underLowerFlag = false
        }
        else{
            resetAdvantage(matchScore)
            stats["advantage"] = HAVE_ADVANTAGE
        }
    }

    private fun resetAdvantage(matchScore: MatchScoreModel){
        matchScore.statsPlayer1["advantage"] = NOT_HAVE_ADVANTAGE
        matchScore.statsPlayer2["advantage"] = NOT_HAVE_ADVANTAGE
    }

    private fun checkGameFinished(match : MatchScoreModel) {
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
                stats["point"] = stats["point"]!! + 10
                isGameOverUnder(matchScore)
            }
            40 -> {
                resetPointScores(matchScore)
                updateGames(matchScore, player)
            }
        }
    }


    private fun isGameOverUnder(matchScore: MatchScoreModel){
        val pointsPlayer1 = matchScore.statsPlayer1["point"]
        val pointsPlayer2 = matchScore.statsPlayer2["point"]
        if (pointsPlayer1 == 40 && pointsPlayer2 == 40) {
            underLowerFlag = true
        }

    }

    private fun updateGames(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        stats["game"] = stats["game"]!! + 1
        if (!tiebreakFlag && isTiebreakState(matchScore)) {
            tiebreakFlag = true
            resetGameScores(matchScore)
        }
        else if (stats["game"] == 7) {
            tiebreakFlag = false
            saveSetResults(matchScore)
            resetGameScores(matchScore)
            updateSets(matchScore, player)
        }
    }

    private fun resetGameScores(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1["game"] = 0
        matchScore.statsPlayer2["game"] = 0
        resetPointScores(matchScore)
    }

    private fun resetPointScores(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1["point"] = 0
        matchScore.statsPlayer2["point"] = 0
    }

    private fun updateSets(matchScore: MatchScoreModel, player: String) {
        val stats = selectPlayerStats(matchScore, player)
        stats["set"] = stats["set"]!! + 1
        checkGameFinished(matchScore)
    }

    private fun saveSetResults(matchScore: MatchScoreModel) {
        matchScore.player1Sets.add(matchScore.statsPlayer1["game"]!!)
        matchScore.player2Sets.add(matchScore.statsPlayer2["game"]!!)
    }
}