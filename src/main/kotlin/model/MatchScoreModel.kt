package model

class MatchScoreModel(
    val match: Match,
    val namePlayer1 : String,
    val namePlayer2 : String
) {
    val statsPlayer1 = mutableMapOf(
        "point" to 0,
        "game" to 0,
        "set" to 0
    )
    val statsPlayer2 = mutableMapOf(
        "point" to 0,
        "game" to 0,
        "set" to 0
    )
    val player1Sets : MutableList<Int> = mutableListOf()
    val player2Sets : MutableList<Int> = mutableListOf()
}