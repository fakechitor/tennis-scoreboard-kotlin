@file:Suppress("ktlint:standard:no-wildcard-imports")

package model

import jakarta.persistence.*

@Entity
@Table(name = "matches")
class Match(
    @ManyToOne
    @JoinColumn(name = "player1")
    var player1: Player? = null,
    @ManyToOne
    @JoinColumn(name = "player2 ")
    var player2: Player? = null,
    @ManyToOne
    @JoinColumn(name = "winner")
    var winner: Player? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null,
)
