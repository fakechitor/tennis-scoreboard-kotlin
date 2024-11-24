package service

import dto.MatchWinnerDto
import dto.MatchesRequestDto
import model.Match
import repository.MatchRepository

private const val OBJECT_ON_PAGE = 5

class FinishedMatchesPersistenceService {
    private val matchRepository = MatchRepository()

    fun getMatches(matchesReqDto: MatchesRequestDto): List<MatchWinnerDto> {
        val skipRowsCount = (matchesReqDto.page - 1) * OBJECT_ON_PAGE
        val maxRowsCount = 5
        if (matchesReqDto.filterName == "") {
            val matches = matchRepository.getMatchesResults(maxRowsCount, skipRowsCount)
            return matches
        } else {
            val matches =
                matchRepository.getFilteredMatchesResults(matchesReqDto.filterName, maxRowsCount, skipRowsCount)
            return matches
        }
    }

    fun getMatchesAmount(): Int {
        return matchRepository.getAll().size
    }
    fun save(match: Match): Match {
        return matchRepository.save(match)
    }
}