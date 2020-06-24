package com.example.moviesearch.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MoviewDetails
import com.example.moviesearch.data.repository.MoviesRepository
import com.example.moviesearch.utils.ApiException
import com.example.moviesearch.utils.Coroutines
import com.example.moviesearch.utils.NoInternetException

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var moviesList = MutableLiveData<List<Movie>>()
    private var movieDetails = MutableLiveData<MoviewDetails>()
    private var errorMessage = MutableLiveData<String>()
    private var detailsMovie = MutableLiveData<String>()

    fun getMoviesList(searchKey : String) {
        Coroutines.main {
            try {
                val result = moviesRepository.getMovies(searchKey)
                if(result.response) {
                    moviesList.value = result.movies
                    moviesRepository.insertMovies(result.movies)
                } else {
                   moviesList.value = moviesRepository.getMoviesDb(searchKey)
                    errorMessage.value = "Failed to fetch information from network!!"
                }
            } catch (e: ApiException) {
                errorMessage.value = e.message
            } catch (e : NoInternetException) {
                moviesList.value = moviesRepository.getMoviesDb(searchKey)
                errorMessage.value = e.message
            }
        }
    }

    fun getMoviesDetails(title : String) {
        Coroutines.main {
            try {
                val result = moviesRepository.getMoviewDetails(title)
                if(result.response) {
                    movieDetails.value = result
                    moviesRepository.insertMovieDetails(result)
                } else {
                    movieDetails.value =  moviesRepository.getMoviesDetailsDb(title)
                    errorMessage.value = "Failed to fetch information from network!!"
                }
            } catch (e: ApiException) {
                errorMessage.value = e.message
            } catch (e : NoInternetException) {
                movieDetails.value = moviesRepository.getMoviesDetailsDb(title)
                errorMessage.value = e.message
            }
        }
    }

    fun getErrorMessage() : LiveData<String> {
        return errorMessage
    }

    fun getMiviesLivedata() : LiveData<List<Movie>> {
        return moviesList
    }

    fun getMovieDetailsLivedata() : LiveData<MoviewDetails> {
        return movieDetails
    }

    fun getMovieDetailsLiveData() : LiveData<String> {
        return detailsMovie
    }

    fun gotoDetailsFragment(title: String) {
        detailsMovie.value = title
    }

}