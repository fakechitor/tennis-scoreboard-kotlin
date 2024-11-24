package service

import constant.GameState
import exception.GameFinishedException
import model.MatchScoreModel
import model.PlayerStats
import kotlin.math.abs

private const val WINNING_SETS_COUNT = 2
private const val HAVE_ADVANTAGE = true
private const val NOT_HAVE_ADVANTAGE = false
private const val REQUIRED_DIFFERENCE = 2
// required for tiebreak/matches where difference between game score == 2
private const val GAMES_REQUIRED_COUNT = 7
// required for matches where difference between game score > 2
private const val GAMES_REQUIRED_COUNT_2 = 6




class MatchScoreCalculationService {
    private var tiebreakFlag: Boolean = false
    private var underLowerFlag: Boolean = false

    fun updateMatchState(matchScore: MatchScoreModel, player: String) {
        when {
            tiebreakFlag -> updateGames(matchScore, player)
            underLowerFlag -> handleAdvantageCase(matchScore, player)
            else -> updateScoreStats(matchScore, player)
        }
    }

    fun getMatchState(): GameState {
        return when {
            tiebreakFlag -> GameState.TIEBREAK
            underLowerFlag -> GameState.UNDER_LOWER
            else -> GameState.NORMAL
        }
    }

    private fun handleAdvantageCase(matchScore: MatchScoreModel, player: String) {
        val scoredPlayerStats = getScoredPlayerStats(matchScore, player)
        val anotherPlayerStats = getAnotherPlayerStats(matchScore, player)
        when (HAVE_ADVANTAGE) {
            scoredPlayerStats.advantage -> {
                resetAdvantage(matchScore)
                resetPointScores(matchScore)
                updateGames(matchScore, player)
                underLowerFlag = false
            }

            anotherPlayerStats.advantage -> {
                resetAdvantage(matchScore)
            }

            else -> {
                resetAdvantage(matchScore)
                scoredPlayerStats.advantage = HAVE_ADVANTAGE
            }
        }
    }

    private fun resetAdvantage(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1.advantage = NOT_HAVE_ADVANTAGE
        matchScore.statsPlayer2.advantage = NOT_HAVE_ADVANTAGE
    }

    private fun checkGameFinished(match: MatchScoreModel) {
        if (match.statsPlayer1.set == WINNING_SETS_COUNT) {
            saveSetResults(match)
            match.match.winner = match.match.player1
            throw GameFinishedException()
        } else if (match.statsPlayer2.set == WINNING_SETS_COUNT) {
            saveSetResults(match)
            match.match.winner = match.match.player2
            throw GameFinishedException()
        }
    }

    private fun updateScoreStats(matchScore: MatchScoreModel, player: String) {
        updatePoints(matchScore, player)
    }

    private fun getScoredPlayerStats(matchScore: MatchScoreModel, scoredPlayer: String): PlayerStats {
        return when (scoredPlayer) {
            "player1" -> matchScore.statsPlayer1
            else -> matchScore.statsPlayer2
        }
    }

    private fun getAnotherPlayerStats(matchScore: MatchScoreModel, player: String): PlayerStats {
        return when (player) {
            "player1" -> matchScore.statsPlayer2
            else -> matchScore.statsPlayer1
        }
    }

    private fun isTiebreakState(matchScore: MatchScoreModel): Boolean {
        val gameCountPlayer1 = matchScore.statsPlayer1.game
        val gameCountPlayer2 = matchScore.statsPlayer2.game
        return gameCountPlayer1 == 6 && gameCountPlayer2 == 6
    }


    private fun updatePoints(matchScore: MatchScoreModel, player: String) {
        val stats = getScoredPlayerStats(matchScore, player)
        when (stats.point) {
            0 -> stats.point += 15
            15 -> stats.point += 15
            30 -> {
                stats.point += 10
                isGameOverUnder(matchScore)
            }

            40 -> {
                resetPointScores(matchScore)
                updateGames(matchScore, player)
            }
        }
    }


    private fun isGameOverUnder(matchScore: MatchScoreModel) {
        val pointsPlayer1 = matchScore.statsPlayer1.point
        val pointsPlayer2 = matchScore.statsPlayer2.point
        if (pointsPlayer1 == 40 && pointsPlayer2 == 40) {
            underLowerFlag = true
        }

    }

    private fun updateGames(matchScore: MatchScoreModel, player: String) {
        val stats = getScoredPlayerStats(matchScore, player)
        stats.game = stats.game + 1
        if (tiebreakFlag && stats.game == GAMES_REQUIRED_COUNT) {
            tiebreakFlag = false
            saveSetResults(matchScore)
            resetGameScores(matchScore)
            updateSets(matchScore, player)
        } else if (!tiebreakFlag && isTiebreakState(matchScore)) {
            tiebreakFlag = true
            resetGameScores(matchScore)
        }
        else if (canWinGameWithoutTiebreak(matchScore, player)) {
            tiebreakFlag = false
            saveSetResults(matchScore)
            resetGameScores(matchScore)
            updateSets(matchScore, player)
        }
    }

    private fun canWinGameWithoutTiebreak(matchScore : MatchScoreModel, player: String): Boolean{
        val stats = getScoredPlayerStats(matchScore, player)
        return !tiebreakFlag && (stats.game == GAMES_REQUIRED_COUNT_2 || stats.game == GAMES_REQUIRED_COUNT) && isScoreDifference(matchScore)
    }

    private fun isScoreDifference(matchScore: MatchScoreModel): Boolean {
        val gamesCountPlayer1 = matchScore.statsPlayer1.game
        val gameCountPlayer2 = matchScore.statsPlayer2.game
        return abs(gamesCountPlayer1 - gameCountPlayer2) >= REQUIRED_DIFFERENCE
    }

    private fun resetGameScores(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1.game = 0
        matchScore.statsPlayer2.game = 0
        resetPointScores(matchScore)
    }

    private fun resetPointScores(matchScore: MatchScoreModel) {
        matchScore.statsPlayer1.point = 0
        matchScore.statsPlayer2.point = 0
    }

    private fun updateSets(matchScore: MatchScoreModel, player: String) {
        val stats = getScoredPlayerStats(matchScore, player)
        stats.set += 1
        checkGameFinished(matchScore)
    }

    private fun saveSetResults(matchScore: MatchScoreModel) {
        matchScore.player2Sets.add(matchScore.statsPlayer2.game)
        matchScore.player1Sets.add(matchScore.statsPlayer1.game)
    }
}