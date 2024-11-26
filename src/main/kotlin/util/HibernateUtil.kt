package util

import model.Match
import model.Player
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object HibernateUtil {
    val sessionFactory = buildSessionFactory()

    private fun buildSessionFactory(): SessionFactory {
        try {
            return MetadataSources(StandardServiceRegistryBuilder().build())
                .addAnnotatedClass(Match::class.java)
                .addAnnotatedClass(Player::class.java)
                .buildMetadata()
                .buildSessionFactory()
        } catch (e: Throwable) {
            throw e
        }
    }

    fun shutdown() {
        sessionFactory.close()
    }
}
