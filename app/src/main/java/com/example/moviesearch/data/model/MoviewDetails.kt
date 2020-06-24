package com.example.moviesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
const val CURRENT_ID = 0
@Entity
data class MoviewDetails (

	@SerializedName("Title") val title : String,
	@SerializedName("Year") val year : Int,
	@SerializedName("Rated") val rated : String,
	@SerializedName("Released") val released : String,
	@SerializedName("Genre") val genre : String,
	@SerializedName("Plot") val plot : String,
	@SerializedName("Language") val language : String,
	@SerializedName("Poster") val poster : String,
	@SerializedName("Response") val response : Boolean
){
	@PrimaryKey(autoGenerate = true)
	var uid : Int = CURRENT_MOVIE_ID
}