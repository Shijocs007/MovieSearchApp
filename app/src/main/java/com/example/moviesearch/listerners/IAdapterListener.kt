package com.example.moviesearch.listerners

import com.example.moviesearch.data.model.Movie

interface IAdapterListener {
    fun onItemClicked(movie: Movie)
}