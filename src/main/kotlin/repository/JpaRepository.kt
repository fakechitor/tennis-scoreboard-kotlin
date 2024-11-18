package repository

interface JpaRepository<T> {
    fun getById(id: Int): T?
    fun getAll(): List<T>?
    fun save(entity: T) : T

}