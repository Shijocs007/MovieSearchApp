package com.example.moviesearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.databinding.ActivityMoviesBinding
import com.example.moviesearch.listerners.IFragmentListener
import com.example.moviesearch.viewmodel.MoviesViewModel
import com.example.moviesearch.viewmodel.MoviesViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MoviesActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : MoviesViewModelFactory by instance()
    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMoviesBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
        viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.getMovieDetailsLiveData().observe(this, Observer {
            it.let {
                var bundle = Bundle()
                bundle.putString("title", it)
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.moviewDetailsFragment, bundle)
            }
        })
    }
}
