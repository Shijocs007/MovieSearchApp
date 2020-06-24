package com.example.moviesearch.data.network

import com.example.moviesearch.data.model.MoviesResult
import com.example.moviesearch.data.model.MoviewDetails
import com.example.moviesearch.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @GET("/")
    suspend fun getMovies(@Query("type") type : String, @Query("page") page : Int, @Query("s") searchKey : String) : Response<MoviesResult>

    @GET("/")
    suspend fun getMovieDetails(@Query("plot") plot : String, @Query("t") name : String) : Response<MoviewDetails>


    companion object {
        operator fun invoke(
            networkConnectionIntercepter: NetworkConnectionIntercepter
        ) : MyApi {

            val okhttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionIntercepter)
                .build()


            return Retrofit.Builder()
                .client(okhttpclient)
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}