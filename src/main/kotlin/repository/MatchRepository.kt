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
                SELECT 
                    p1.name AS player1_name,
                    p2.name AS player2_name,
                    p3.name AS winner_name
                FROM Match m
                JOIN Player p1 ON m.player1 = p1.id
                JOIN Player p2 ON m.player2 = p2.id
                JOIN Player p3 ON m.winner = p3.id
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
                SELECT 
                    p1.name AS player1_name,
                    p2.name AS player2_name,
                    p3.name AS winner_name
                FROM Match m
                JOIN Player p1 ON m.player1 = p1.id
                JOIN Player p2 ON m.player2 = p2.id
                JOIN Player p3 ON m.winner = p3.id
                WHERE p1.name = :name OR p2.name = :name
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
            JOIN Player p1 ON m.player1 = p1.id
            JOIN Player p2 ON m.player2 = p2.id
            WHERE p1.name = :name OR p2.name = :name
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
