package com.example.movieapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String? = null,
    val released: String? = null,
    val runtime: String? = null,
    val genre: String? = null,
    val director: String? = null,
    val writer: String? = null,
    val actors: String? = null,
    val plot: String? = null,
    val language: String? = null,
    val country: String? = null,
    val awards: String? = null,
    val poster: String? = null,
    val metascore: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val type: String? = null
)