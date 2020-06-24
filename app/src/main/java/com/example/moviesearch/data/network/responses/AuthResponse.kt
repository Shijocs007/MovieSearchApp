package com.example.moviesearch.data.network.responses

import com.example.moviesearch.data.db.entities.User

data class AuthResponse (
    val isSuccessful : Boolean?,
    val message : String?,
    val user : User?
)