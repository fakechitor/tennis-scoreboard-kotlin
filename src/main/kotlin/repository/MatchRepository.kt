package repository

import model.Match
import model.Player
import org.hibernate.Session
import util.HibernateUtil

class MatchRepository : JpaRepository<Match> {
    private val sessionFactory = HibernateUtil.sessionFactory

    override fun getById(id: Int): Match? {
        var session: Session? = null
        var match: Match? = null

        try {
            session = sessionFactory.openSession()
            val hql = "FROM Match m WHERE m.id = :id"
            val query = session.createQuery(hql, Match::class.java)
            query.setParameter("id", id)
            val result = query.resultList
            if (result.size > 0 ) {
                match = result[0]
            }
        } catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()
        }
        finally {
            session?.close()
        }
        return match
    }

    override fun getAll(): List<Match>? {
        var session : Session? = null
        var matches : List<Match> = listOf()
        try{
            session = sessionFactory.openSession()
            val hql = "FROM Match"
            val query = session.createQuery(hql, Match::class.java)
            matches = query.resultList.toList()
        }catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()

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
        }
        finally {
            session?.close()
        }
    }
}