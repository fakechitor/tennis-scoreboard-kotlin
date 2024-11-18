package service

import model.Match
import repository.MatchRepository

class FinishedMatchesPersistenceService {
    private val matchRepository = MatchRepository()

    fun getById(id : Int): Match? {
        return matchRepository.getById(id)
    }

    fun getAll(): List<Match>? {
        return matchRepository.getAll()
    }

    fun save(match: Match): Match {
        return matchRepository.save(match)
    }
}