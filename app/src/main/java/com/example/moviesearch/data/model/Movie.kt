package com.example.moviesearch.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_MOVIE_ID = 0
@Entity
data class Movie (

	@SerializedName("Title") val title : String,
	@SerializedName("Year") val year : Int,
	@SerializedName("imdbID") val imdbID : String,
	@SerializedName("Type") val type : String,
	@SerializedName("Poster") val poster : String
){
	@PrimaryKey(autoGenerate = true)
	var uid : Int = CURRENT_MOVIE_ID
}