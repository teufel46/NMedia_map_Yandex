package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Marker
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.MarkerRepository
import ru.netology.nmedia.repository.MarkerRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Marker(
    id = 0,
    name = "",
    latitude = 0.0,
    longitude = 0.0,
)

@ExperimentalCoroutinesApi
class MarkerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MarkerRepository =
        MarkerRepositoryImpl(AppDb.getInstance(context = application).markerDao())

    val data: LiveData<FeedModel> = repository.data
        .map { markers ->
            FeedModel(
                markers,
                markers.isEmpty()
            )
        }
        .asLiveData(Dispatchers.Default)

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val markerCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadMarkers()
    }

    fun loadMarkers() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun refreshMarkers() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(refreshing = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun save() {
        edited.value?.let {
            _postCreated.value = Unit
            viewModelScope.launch {
                try {
                    repository.save(it)
                    _dataState.value = FeedModelState()
                } catch (e: Exception) {
                    _dataState.value = FeedModelState(error = true)
                }
            }
        }
        edited.value = empty
    }

    fun edit(marker: Marker) {
        edited.value = marker
    }

    fun changeContent(name: String, latitude: Double, longitude: Double) {
        if (edited.value?.name == name && edited.value?.latitude == latitude && edited.value?.longitude == longitude) {
            return
        }
        edited.value = edited.value?.copy(name = name, latitude = latitude, longitude = longitude)
        return
    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }
}
