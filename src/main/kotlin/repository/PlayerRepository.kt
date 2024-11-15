package repository

import dto.PlayerDto
import model.Player
import org.hibernate.Session
import util.HibernateUtil

class PlayerRepository : JpaRepository<Player, PlayerDto> {
    private val sessionFactory = HibernateUtil.sessionFactory

    override fun getById(): Player {
        TODO()
    }

    override fun getByName(name: String): Player? {
        var session: Session? = null
        var player: Player? = null

        try {
            session = sessionFactory.openSession()
            val hql = "FROM Player p WHERE p.name = :name"
            val query = session.createQuery(hql, Player::class.java)
            query.setParameter("name", name)
            val result = query.resultList
            if (result.size > 0 ) {
                player = result[0]
            }
        } catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()
        }
        finally {
            session?.close()
        }
        return player
    }

    override fun getAll(): List<Player> {
        var session : Session? = null
        var players : List<Player> = listOf()
        try{
            session = sessionFactory.openSession()
            val hql = "FROM Player"
            val query = session.createQuery(hql, Player::class.java)
            players = query.resultList.toList()
        }catch (e: Exception) {
            session?.transaction?.rollback()
            e.printStackTrace()

        }
        return players
    }

    override fun save(entity: Player): Player {
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