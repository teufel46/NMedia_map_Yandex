package ru.netology.nmedia.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.entity.MarkerEntity


@Dao
interface MarkerDao {
    @Query("SELECT * FROM MarkerEntity ORDER BY id DESC")
    fun getAll(): Flow<List<MarkerEntity>>

    @Query("SELECT COUNT(*) == 0 FROM MarkerEntity")
    suspend fun isEmpty(): Boolean

    @Query("SELECT COUNT(*) FROM MarkerEntity")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: MarkerEntity)

    @Query("DELETE FROM MarkerEntity WHERE id = :id")
    suspend fun removeById(id: Long)
}

