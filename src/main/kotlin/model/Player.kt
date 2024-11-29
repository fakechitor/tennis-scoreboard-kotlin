@file:Suppress("ktlint:standard:no-wildcard-imports")

package model

import jakarta.persistence.*

@Entity
@Table(name = "players")
class Player(
    @Column(name = "name")
    var name: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,
)
