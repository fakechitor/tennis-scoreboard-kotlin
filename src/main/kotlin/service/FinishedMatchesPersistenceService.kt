package service

import dto.MatchWinnerDto
import dto.MatchesRequestDto
import model.Match
import repository.MatchRepository

private const val OBJECT_ON_PAGE = 5

class FinishedMatchesPersistenceService {
    private val matchRepository = MatchRepository()

    fun getMatches(matchesReqDto : MatchesRequestDto): List<MatchWinnerDto> {
        if (matchesReqDto.filterName == "") {
            val skipRowsCount = ( matchesReqDto.page - 1 ) * OBJECT_ON_PAGE
            val matches = matchRepository.getMatchesResults(OBJECT_ON_PAGE * matchesReqDto.page, skipRowsCount)
            return matches
        }
        else {
            TODO()
        }
    }

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