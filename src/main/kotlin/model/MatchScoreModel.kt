package model

class MatchScoreModel(
    val match: Match,
    val namePlayer1 : String,
    val namePlayer2 : String
) {
    val setsPlayer1: Int = 0
    val gamesPlayer1: Int = 0
    val pointsPlayer1: Int = 0
    val setsPlayer2: Int = 0
    val gamesPlayer2: Int = 0
    val pointsPlayer2: Int = 0
}