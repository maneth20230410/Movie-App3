package com.example.movieapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.local.daos.ActorDao
import com.example.movieapp.data.local.daos.MovieDao
import com.example.movieapp.data.local.entities.ActorEntity
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val actorDao: ActorDao = MovieDatabase.getDatabase(application).actorDao()
    private val apiService = RetrofitClient.omdbApiService
    private val apiKey = RetrofitClient.API_KEY

    private val _searchResults = MutableStateFlow<List<MovieEntity>>(emptyList())
    val searchResults: StateFlow<List<MovieEntity>> = _searchResults.asStateFlow()

    private val _savedMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val savedMovies: StateFlow<List<MovieEntity>> = _savedMovies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    init {
        loadSavedMovies()
    }

    private fun loadSavedMovies() {
        viewModelScope.launch {
            movieDao.getAllMovies().collect {
                _savedMovies.value = it
            }
        }
    }

    fun searchMovie(title: String) {
        if (title.isBlank()) {
            _message.value = "Please enter a movie title"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _message.value = null
            try {
                val response = apiService.searchMovieByTitle(title, apiKey)
                if (response.response == "True") {
                    _searchResults.value = listOf(response.toMovieEntity())
                } else {
                    _searchResults.value = emptyList()
                    _message.value = "Movie not found"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
                Log.e("AddMoviesViewModel", "Error searching movie", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMovie(movie: MovieEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Check if movie already exists
                val existingMovie = movieDao.getMovieById(movie.imdbID)
                if (existingMovie != null) {
                    _message.value = "Movie already in database"
                } else {
                    // Get full details
                    val movieDetails = apiService.getMovieDetails(movie.imdbID, apiKey)
                    if (movieDetails.response == "True") {
                        val fullMovieEntity = movieDetails.toMovieEntity()

                        // Save movie
                        movieDao.insertMovie(fullMovieEntity)

                        // Extract and save actors
                        movieDetails.actors?.split(", ")?.forEach { actorName ->
                            if (actorName.isNotBlank()) {
                                val actor = ActorEntity(
                                    name = actorName.trim(),
                                    movieId = movie.imdbID
                                )
                                actorDao.insertActor(actor)
                            }
                        }

                        _message.value = "Movie saved successfully"
                        loadSavedMovies()
                    }
                }
            } catch (e: Exception) {
                _message.value = "Error saving movie: ${e.message}"
                Log.e("AddMoviesViewModel", "Error saving movie", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}