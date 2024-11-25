package repository

import model.Player
import org.hibernate.Session
import org.hibernate.Transaction
import util.HibernateUtil

class PlayerRepository : JpaRepository<Player> {
    private val sessionFactory = HibernateUtil.sessionFactory

    override fun save(entity: Player): Player {
        return executeInTransaction { session ->
            session.persist(entity)
            entity
        }
    }

    fun getByName(name: String): Player? {
        return execute { session ->
            val hql = "FROM Player p WHERE p.name = :name"
            session.createQuery(hql, Player::class.java)
                .setParameter("name", name)
                .uniqueResultOptional()
                .orElse(null)
        }
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
