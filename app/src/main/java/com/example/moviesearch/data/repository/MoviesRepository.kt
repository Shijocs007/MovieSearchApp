package com.example.moviesearch.data.repository

import androidx.room.PrimaryKey
import com.example.moviesearch.data.db.AppDataBase
import com.example.moviesearch.data.db.entities.User
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MoviesResult
import com.example.moviesearch.data.model.MoviewDetails
import com.example.moviesearch.data.network.MyApi
import com.example.moviesearch.data.network.SafeApiRequest
import com.example.moviesearch.data.network.responses.AuthResponse


class MoviesRepository(
    private val api : MyApi,
    private val db : AppDataBase
) : SafeApiRequest() {

    suspend fun getMovies(searchKey: String) : MoviesResult {

        return apiRequest { api.getMovies("movie", 1,  searchKey) }

    }

    suspend fun getMoviewDetails(name: String) : MoviewDetails{
        return apiRequest { api.getMovieDetails("full", name) }
    }

    suspend fun insertMovies(movies: List<Movie>) = db.getMovieDao().upsertAll(movies)

    suspend fun getMoviesDb(searchKey : String) = db.getMovieDao().getMovies(searchKey)

    suspend fun insertMovieDetails(movie: MoviewDetails) = db.getMovieDao().upsertMoviewDetails(movie)

    suspend fun getMoviesDetailsDb(title : String) = db.getMovieDao().getMoviewDetails(title)

}