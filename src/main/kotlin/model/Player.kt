package model

import jakarta.persistence.*

@Entity
@Table(name = "players")
class Player{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0;
    @Column(name = "name")
    var name: String = "";
    constructor() {}
    constructor(name : String) : this() {
        this.name = name
    }

    override fun toString(): String {
        return "Player(id=$id, name='$name')"
    }


}