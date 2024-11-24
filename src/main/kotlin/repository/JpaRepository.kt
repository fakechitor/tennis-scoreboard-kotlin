package repository

interface JpaRepository<T> {
    fun save(entity: T): T
}