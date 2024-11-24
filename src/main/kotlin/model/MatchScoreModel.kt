package model

class MatchScoreModel(
    val match: Match,
    val namePlayer1: String,
    val namePlayer2: String
) {
    val player1Sets: MutableList<Int> = mutableListOf()
    val player2Sets: MutableList<Int> = mutableListOf()

    val statsPlayer1 = PlayerStats()
    val statsPlayer2 = PlayerStats()
//    var statsPlayer1 = mutableMapOf(
//        "point" to 0,
//        "game" to 0,
//        "set" to 0,
//        "advantage" to 0
//    )
//    var statsPlayer2 = mutableMapOf(
//        "point" to 0,
//        "game" to 0,
//        "set" to 0,
//        "advantage" to 0
//    )
}