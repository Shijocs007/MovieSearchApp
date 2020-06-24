package com.example.moviesearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MoviewDetails


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies : List<Movie>)

    @Query("SELECT * FROM movie where title LIKE  '%' || :searchKey || '%'")
    suspend fun getMovies(searchKey : String) : List<Movie>

    @Query("SELECT * FROM moviewdetails where title = :titleKey")
    suspend fun getMoviewDetails(titleKey : String) : MoviewDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMoviewDetails(movie : MoviewDetails)
}