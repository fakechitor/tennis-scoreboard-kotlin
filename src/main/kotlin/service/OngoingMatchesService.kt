package service

import model.MatchScoreModel
import java.util.concurrent.ConcurrentHashMap

object OngoingMatchesService {
    private val map = ConcurrentHashMap<String, MatchScoreModel>()

    fun getMatch(id: String): MatchScoreModel? {
        return map[id]
    }

    fun putMatch(id: String, match: MatchScoreModel) {
        map[id] = match
    }

    fun printData() {
        for (uuid in map.keys()) {
            println(map[uuid].toString())
        }
    }

}