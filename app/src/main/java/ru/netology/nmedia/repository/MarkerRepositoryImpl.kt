package ru.netology.nmedia.repository

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.netology.nmedia.dao.MarkerDao
import ru.netology.nmedia.dto.*
import ru.netology.nmedia.entity.MarkerEntity
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException

class MarkerRepositoryImpl(private val dao: MarkerDao) : MarkerRepository {
    override val data = dao.getAll()
        .map(List<MarkerEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            dao.getAll()
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

     override suspend fun save(marker: Marker) {
        try {
            dao.insert(MarkerEntity.fromDto(marker))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            dao.removeById(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}
