package repository

import dto.MatchWinnerDto
import model.Match
import org.hibernate.Session
import util.HibernateUtil

class MatchRepository : JpaRepository<Match> {
    private val sessionFactory = HibernateUtil.sessionFactory

    fun getMatchesResults(limit: Int, offset: Int): List<MatchWinnerDto> {
        var session: Session? = null
        var matches: List<MatchWinnerDto> = listOf()
        try {
            session = sessionFactory.openSession()
            val hql = """
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
            val result = query.resultList

            matches = result.map { row ->
                MatchWinnerDto(
                    name1 = row[0] as String,
                    name2 = row[1] as String,
                    winner = row[2] as String
                )
            }
        } catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()
        } finally {
            session?.close()
        }
        return matches
    }

    fun getFilteredMatchesResults(name: String, limit: Int, offset: Int): List<MatchWinnerDto> {
        var session: Session? = null
        var matches: List<MatchWinnerDto> = listOf()
        try {
            session = sessionFactory.openSession()
            val hql = """
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
            val result = query.resultList

            matches = result.map { row ->
                MatchWinnerDto(
                    name1 = row[0] as String,
                    name2 = row[1] as String,
                    winner = row[2] as String
                )
            }
        } catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()
        } finally {
            session?.close()
        }
        return matches
    }

    override fun save(entity: Match): Match {
        var session: Session? = null
        try {
            session = sessionFactory.openSession()
            session.beginTransaction()
            session.persist(entity)
            session.transaction.commit()
            return entity
        } catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()
            throw e
        } finally {
            session?.close()
        }
    }
}