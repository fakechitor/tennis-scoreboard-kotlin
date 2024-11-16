package model

class MatchScoreModel(
    val match: Match,
    val namePlayer1 : String,
    val namePlayer2 : String
) {
    var setsPlayer1: Int = 0
    var gamesPlayer1: Int = 0
    var pointsPlayer1: Int = 0
    var setsPlayer2: Int = 0
    var gamesPlayer2: Int = 0
    var pointsPlayer2: Int = 0
}