package repository

import org.hibernate.Session
import org.hibernate.Transaction
import util.HibernateUtil

abstract class JpaRepository {
    private val sessionFactory = HibernateUtil.sessionFactory

    abstract fun <T> save(entity: T): T

    protected fun <T> execute(action: (Session) -> T): T {
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

    protected fun <T> executeInTransaction(action: (Session) -> T): T {
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
