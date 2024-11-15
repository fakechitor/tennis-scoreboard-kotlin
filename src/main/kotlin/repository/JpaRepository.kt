package repository

interface JpaRepository<T, K> {
    fun getById(): T
    fun getByName(name: String): T?
    fun getAll(): List<T>?
    fun save(entity: T) : T

}