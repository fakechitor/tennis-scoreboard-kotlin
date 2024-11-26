package service

import dto.PlayerDto
import model.Player
import org.modelmapper.ModelMapper
import repository.PlayerRepository

object PlayerService {
    private val playerRepository = PlayerRepository
    private var mapper = ModelMapper()

    fun findOrCreatePlayer(name: String): Player {
        val player = playerRepository.getByName(name) ?: return save(PlayerDto(name))
        return player
    }

    private fun save(playerDto: PlayerDto): Player {
        val player = mapper.map(playerDto, Player::class.java)
        return playerRepository.save(player)
    }
}
