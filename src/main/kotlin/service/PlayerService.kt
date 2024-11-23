package service

import dto.PlayerDto
import model.Player
import org.modelmapper.ModelMapper
import repository.PlayerRepository

class PlayerService {
    private val playerRepository = PlayerRepository()
    private var mapper = ModelMapper()

    fun getByName(name: String) : Player? {
        return playerRepository.getByName(name)
    }
    fun save(playerDto: PlayerDto) : Player {
        val player = mapper.map(playerDto, Player::class.java)
        return playerRepository.save(player)
    }
}