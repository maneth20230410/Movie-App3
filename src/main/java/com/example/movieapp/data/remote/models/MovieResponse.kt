package com.example.movieapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String?,
    @SerializedName("Released") val released: String?,
    @SerializedName("Runtime") val runtime: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Writer") val writer: String?,
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Language") val language: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Awards") val awards: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Metascore") val metascore: String?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("imdbVotes") val imdbVotes: String?,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String?,
    @SerializedName("Response") val response: String
) {
    fun toMovieEntity(): com.example.movieapp.data.local.entities.MovieEntity {
        return com.example.movieapp.data.local.entities.MovieEntity(
            imdbID = imdbID,
            title = title,
            year = year,
            rated = rated,
            released = released,
            runtime = runtime,
            genre = genre,
            director = director,
            writer = writer,
            actors = actors,
            plot = plot,
            language = language,
            country = country,
            awards = awards,
            poster = poster,
            metascore = metascore,
            imdbRating = imdbRating,
            imdbVotes = imdbVotes,
            type = type
        )
    }
}
