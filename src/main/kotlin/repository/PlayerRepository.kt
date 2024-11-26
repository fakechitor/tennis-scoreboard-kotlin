package repository

import model.Player

object PlayerRepository : JpaRepository() {
    override fun <Player> save(entity: Player): Player =
        executeInTransaction { session ->
            session.persist(entity)
            entity
        }

    fun getByName(name: String): Player? =
        execute { session ->
            val hql = "FROM Player p WHERE p.name = :name"
            session
                .createQuery(hql, Player::class.java)
                .setParameter("name", name)
                .uniqueResultOptional()
                .orElse(null)
        }
}
