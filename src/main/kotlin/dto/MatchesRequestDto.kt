package dto

data class MatchesRequestDto (
    val page : Int = 1,
    val filterName: String = "",
)