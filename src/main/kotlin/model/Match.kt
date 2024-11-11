package model

import jakarta.persistence.*

@Entity
@Table(name = "matches")
class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int = 0
    @Column(name = "player1")
    var player1: Int = 0
    @Column(name = "player2")
    var player2: Int = 0
    @Column(name = "winner")
    var winner : Int = 0

    constructor(){}

    constructor(player1: Int, player2: Int, winner: Int) : this() {
        this.player1 = player1
        this.player2 = player2
        this.winner = winner
    }
}

