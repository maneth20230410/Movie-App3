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
import retrofit2.HttpException
import java.io.IOException

class MovieSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val actorDao: ActorDao = MovieDatabase.getDatabase(application).actorDao()
    private val apiService = RetrofitClient.omdbApiService
    private val apiKey = RetrofitClient.API_KEY

    private val _searchResults = MutableStateFlow<List<MovieEntity>>(emptyList())
    val searchResults: StateFlow<List<MovieEntity>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _errorMessage.value = "Please enter a movie title"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.searchMovies(query, apiKey)
                if (response.response == "True" && !response.search.isNullOrEmpty()) {
                    val basicMovies = response.search.map { it.toBasicMovieEntity() }
                    _searchResults.value = basicMovies
                } else {
                    _searchResults.value = emptyList()
                    _errorMessage.value = "No movies found"
                }
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
                Log.e("MovieSearchViewModel", "Network error", e)
            } catch (e: HttpException) {
                _errorMessage.value = "HTTP error: ${e.message}"
                Log.e("MovieSearchViewModel", "HTTP error", e)
            } catch (e: Exception) {
                _errorMessage.value = "Unknown error: ${e.message}"
                Log.e("MovieSearchViewModel", "Unknown error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMovieDetails(imdbId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movieDetails = apiService.getMovieDetails(imdbId, apiKey)
                if (movieDetails.response == "True") {
                    val movieEntity = movieDetails.toMovieEntity()
                    _searchResults.value = listOf(movieEntity)
                }
            } catch (e: Exception) {
                Log.e("MovieSearchViewModel", "Error fetching movie details", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMovieToDatabase(movie: MovieEntity) {
        viewModelScope.launch {
            try {
                // First, get full details if we only have basic info
                val movieDetails = apiService.getMovieDetails(movie.imdbID, apiKey)
                if (movieDetails.response == "True") {
                    val fullMovieEntity = movieDetails.toMovieEntity()

                    // Save the movie
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
                }
            } catch (e: Exception) {
                Log.e("MovieSearchViewModel", "Error saving movie", e)
            }
        }
    }
}