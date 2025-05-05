package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.models.MovieResponse
import com.example.movieapp.data.remote.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMoviesByTitle(
        @Query("s") searchQuery: String,
        @Query("apikey") apiKey: String
    ): SearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String
    ): MovieResponse

    @GET("/")
    suspend fun searchMovieByTitle(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ): MovieResponse
}