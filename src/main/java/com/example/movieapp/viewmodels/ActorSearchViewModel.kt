package com.example.movieapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActorSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao = MovieDatabase.getDatabase(application).movieDao()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchResults = MutableStateFlow<List<MovieEntity>>(emptyList())
    val searchResults: StateFlow<List<MovieEntity>> = _searchResults

    // Search for movies by actor name
    fun searchMoviesByActor(actorName: String) {
        if (actorName.isBlank()) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                movieDao.searchMoviesByActor(actorName).collect { movies ->
                    _searchResults.value = movies
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}