package com.example.movieapp.data.local.daos

import androidx.room.*
import com.example.movieapp.data.local.entities.ActorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorEntity>)

    @Query("SELECT * FROM actors WHERE name LIKE '%' || :name || '%'")
    fun searchActorsByName(name: String): Flow<List<ActorEntity>>

    @Query("SELECT * FROM actors WHERE movieId = :movieId")
    fun getActorsByMovie(movieId: String): Flow<List<ActorEntity>>

    @Delete
    suspend fun deleteActor(actor: ActorEntity)

    @Query("DELETE FROM actors")
    suspend fun deleteAllActors()
}