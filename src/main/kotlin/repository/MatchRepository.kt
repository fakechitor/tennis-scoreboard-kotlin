package repository

import dto.MatchWinnerDto
import model.Match

object MatchRepository : JpaRepository() {
    fun getMatchesResults(
        limit: Int,
        offset: Int,
    ): List<MatchWinnerDto> =
        execute { session ->
            val hql =
                """
                SELECT m.player1.name, m.player2.name, m.winner.name
                FROM Match m
            """
            val query =
                session.createQuery(hql, Array<Any>::class.java).apply {
                    setMaxResults(limit)
                    setFirstResult(offset)
                }
            query.resultList.map { row ->
                MatchWinnerDto(
                    name1 = row[0] as String,
                    name2 = row[1] as String,
                    winner = row[2] as String,
                )
            }
        }

    fun getFilteredMatchesResults(
        name: String,
        limit: Int,
        offset: Int,
    ): List<MatchWinnerDto> =
        execute { session ->
            val hql =
                """
                SELECT m.player1.name, m.player2.name, m.winner
                FROM Match m
                WHERE m.player1.name = :name OR m.player2.name = :name
            """
            val query =
                session.createQuery(hql, Array<Any>::class.java).apply {
                    setParameter("name", name)
                    maxResults = limit
                    setFirstResult(offset)
                }
            query.resultList.map { row ->
                MatchWinnerDto(
                    name1 = row[0] as String,
                    name2 = row[1] as String,
                    winner = row[2] as String,
                )
            }
        }

    fun getAll(): List<Match> =
        execute { session ->
            val hql = "FROM Match"
            val query = session.createQuery(hql, Match::class.java)
            query.resultList
        }

    fun getAllWithName(name: String): List<Match> =
        execute { session ->
            val hql =
                """
            FROM Match m
                WHERE m.player1.name = :name OR m.player2.name = :name
        """
            val query = session.createQuery(hql, Match::class.java)
            query.setParameter("name", name)
            query.resultList
        }

    override fun <Match> save(entity: Match): Match =
        executeInTransaction { session ->
            session.persist(entity)
            entity
        }
}
