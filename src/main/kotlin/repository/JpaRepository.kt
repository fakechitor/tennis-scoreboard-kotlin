package repository

import org.hibernate.Session
import util.HibernateUtil

abstract class JpaRepository {
    private val sessionFactory = HibernateUtil.sessionFactory

    abstract fun <T> save(entity: T): T

    protected fun <T> execute(action: (Session) -> T): T {
        sessionFactory.openSession().use { session ->
            return action(session)
        }
    }

    protected fun <T> executeInTransaction(action: (Session) -> T): T {
        sessionFactory.openSession().use { session ->
            val transaction = session.beginTransaction()
            return try {
                val result = action(session)
                transaction.commit()
                result
            } catch (e: Exception) {
                transaction.rollback()
                throw RuntimeException("Ошибка с доступом к базе данных", e)
            }
        }
    }
}
