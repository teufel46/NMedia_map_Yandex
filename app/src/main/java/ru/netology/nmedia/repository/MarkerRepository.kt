package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Marker

interface MarkerRepository {
    val data: Flow<List<Marker>>
    suspend fun getAll()
    suspend fun save(marker: Marker)
    suspend fun removeById(id : Long)
}

