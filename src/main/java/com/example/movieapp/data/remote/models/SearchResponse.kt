package com.example.movieapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") val search: List<SearchItem>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String
)

data class SearchItem(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String?,
    @SerializedName("Poster") val poster: String?
) {
    fun toBasicMovieEntity(): com.example.movieapp.data.local.entities.MovieEntity {
        return com.example.movieapp.data.local.entities.MovieEntity(
            imdbID = imdbID,
            title = title,
            year = year,
            type = type,
            poster = poster
        )
    }
}