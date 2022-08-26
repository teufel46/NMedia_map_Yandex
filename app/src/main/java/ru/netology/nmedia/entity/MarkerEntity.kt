package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Marker

@Entity
data class MarkerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name : String,
    val latitude : Double,
    val longitude : Double,
) {
    fun toDto() = Marker(id, name, latitude, longitude)

    companion object {
        fun fromDto(dto: Marker) =
            MarkerEntity(dto.id, dto.name, dto.latitude, dto.longitude)

    }
}

fun List<MarkerEntity>.toDto(): List<Marker> = map(MarkerEntity::toDto)
fun List<Marker>.toEntity(): List<MarkerEntity> = map(MarkerEntity::fromDto)
