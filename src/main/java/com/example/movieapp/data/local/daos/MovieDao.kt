package com.example.movieapp.data.local.daos

import androidx.room.*
import com.example.movieapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :title || '%'")
    fun searchMoviesByTitle(title: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE actors LIKE '%' || :actorName || '%'")
    fun searchMoviesByActor(actorName: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE imdbID = :imdbId")
    suspend fun getMovieById(imdbId: String): MovieEntity?

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}