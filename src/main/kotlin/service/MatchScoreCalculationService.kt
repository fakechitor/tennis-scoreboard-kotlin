package service

import exception.GameFinishedException
import model.Match
import model.MatchScoreModel

private const val WINNING_SETS_COUNT = 2

class MatchScoreCalculationService {

    fun testAddScore(uuid : String, player : String) {
        if (player == "player1"){
            OngoingMatchesService.getMatch(uuid)!!.pointsPlayer1++
        }
        else{
            OngoingMatchesService.getMatch(uuid)!!.pointsPlayer2++
        }
    }

    fun updateMatchState(uuid : String){
        val matchScore = OngoingMatchesService.getMatch(uuid)
        if (matchScore!!.setsPlayer1 == WINNING_SETS_COUNT){
            matchScore.match.winner = matchScore.match.player1
            throw GameFinishedException()
        }
        else if (matchScore.setsPlayer2 == WINNING_SETS_COUNT){
            matchScore.match.winner = matchScore.match.player2
            throw GameFinishedException()
        }
        countScores(matchScore)
    }

    private fun countScores(matchScore : MatchScoreModel) {
        if (matchScore.pointsPlayer1 == 40){

        }
    }

}