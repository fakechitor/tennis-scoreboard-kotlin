package repository

import dto.MatchWinnerDto
import model.Match
import org.hibernate.Session
import org.hibernate.Transaction
import util.HibernateUtil

object MatchRepository : JpaRepository<Match> {
    private val sessionFactory = HibernateUtil.sessionFactory

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
            val query = session.createQuery(hql, Array<Any>::class.java)
            query.setMaxResults(limit)
            query.setFirstResult(offset)
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
            val query = session.createQuery(hql, Array<Any>::class.java)
            query.setParameter("name", name)
            query.setMaxResults(limit)
            query.setFirstResult(offset)
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

    override fun save(entity: Match): Match =
        executeInTransaction { session ->
            session.persist(entity)
            entity
        }

    private fun <T> execute(action: (Session) -> T): T {
        var session: Session? = null
        try {
            session = sessionFactory.openSession()
            return action(session)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Ошибка с доступом к базе данных")
        } finally {
            session?.close()
        }
    }

    private fun <T> executeInTransaction(action: (Session) -> T): T {
        var session: Session? = null
        var transaction: Transaction? = null
        try {
            session = sessionFactory.openSession()
            transaction = session.beginTransaction()
            val result = action(session)
            transaction.commit()
            return result
        } catch (e: Exception) {
            transaction?.rollback()
            e.printStackTrace()
            throw RuntimeException("Ошибка с доступом к базе данных")
        } finally {
            session?.close()
        }
    }
}
