package service

import dto.MatchWinnerDto
import dto.MatchesRequestDto
import model.Match
import repository.MatchRepository

private const val OBJECT_ON_PAGE = 5

object FinishedMatchesPersistenceService {
    private val matchRepository = MatchRepository

    fun getMatches(matchesReqDto: MatchesRequestDto): List<MatchWinnerDto> {
        val skipRowsCount = (matchesReqDto.page - 1) * OBJECT_ON_PAGE
        if (matchesReqDto.filterName.isBlank()) {
            val matches = matchRepository.getMatchesResults(OBJECT_ON_PAGE, skipRowsCount)
            return matches
        } else {
            val matches =
                matchRepository.getFilteredMatchesResults(matchesReqDto.filterName, OBJECT_ON_PAGE, skipRowsCount)
            return matches
        }
    }

    fun getMatchesAmount(name: String): Int {
        if (name == "") {
            return matchRepository.getAll().size
        }
        return matchRepository.getAllWithName(name).size
    }

    fun save(match: Match): Match = matchRepository.save(match)
}
