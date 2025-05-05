package com.example.movieapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.local.daos.MovieDao
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val apiService = RetrofitClient.omdbApiService

    // API key should be stored securely in production app
    private val API_KEY = "YOUR_OMDB_API_KEY"

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = _movies

    // Load movies from the database
    fun loadMoviesFromDb() {
        viewModelScope.launch {
            movieDao.getAllMovies().collect { moviesList ->
                _movies.value = moviesList
            }
        }
    }

    // Function to add a few sample movies to the database
    fun addSampleMoviesToDb() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Get some popular movie details from the API
                val movieTitles = listOf("Matrix", "Shawshank Redemption", "Inception", "Interstellar")

                for (title in movieTitles) {
                    val response = apiService.searchMovieByTitle(title, API_KEY)
                    if (response.response == "True") {
                        // Convert to entity and save to database
                        val movieEntity = response.toMovieEntity()
                        movieDao.insertMovie(movieEntity)
                    }
                }
            } catch (e: Exception) {
                // Handle error - in a real app you'd want better error handling
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}