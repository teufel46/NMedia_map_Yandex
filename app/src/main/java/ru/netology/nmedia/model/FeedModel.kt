package ru.netology.nmedia.model

import ru.netology.nmedia.dto.Marker

data class FeedModel(
    val markers: List<Marker> = emptyList(),
    val empty: Boolean = false,
)
