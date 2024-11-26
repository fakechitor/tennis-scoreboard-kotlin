package service

import model.MatchScoreModel
import java.util.concurrent.ConcurrentHashMap

object OngoingMatchesService {
    private val map = ConcurrentHashMap<String, MatchScoreModel>()

    fun getMatch(id: String): MatchScoreModel {
        val match = map[id] ?: throw IllegalArgumentException("Неверный uuid матча")
        return match
    }

    fun putMatch(
        id: String,
        match: MatchScoreModel,
    ) {
        map[id] = match
    }

    fun deleteMatch(uuid: String) {
        map.remove(uuid)
    }
}
