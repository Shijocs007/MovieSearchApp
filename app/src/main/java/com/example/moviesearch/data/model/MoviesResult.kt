package com.example.moviesearch.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResult (

	@SerializedName("Search") val movies : List<Movie>,
	@SerializedName("totalResults") val totalResults : Int,
	@SerializedName("Response") val response : Boolean
)