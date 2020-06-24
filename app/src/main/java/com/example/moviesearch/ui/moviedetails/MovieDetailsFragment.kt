package com.example.moviesearch.ui.moviedetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentMovieDetailsBinding
import com.example.moviesearch.databinding.FragmentMovieListBinding
import com.example.moviesearch.utils.hideProgress
import com.example.moviesearch.utils.showProgress
import com.example.moviesearch.utils.toast
import com.example.moviesearch.viewmodel.MoviesViewModel
import com.example.moviesearch.viewmodel.MoviesViewModelFactory
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment(), KodeinAware {

    lateinit var binding: FragmentMovieDetailsBinding
    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()

    private val factory : MoviesViewModelFactory by instance()
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, factory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val movieTitle = arguments?.getString("title")

        binding = DataBindingUtil.inflate<FragmentMovieDetailsBinding>(inflater, R.layout.fragment_movie_details, container, false)
        binding.viewmodel = viewModel

        viewModel.getMoviesDetails(movieTitle!!)
        binding.progressBar.showProgress()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMovieDetailsLivedata().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideProgress()
           it.let {
               Picasso.get()
                   .load(it.poster)
                   .placeholder(R.drawable.image_2)
                   .error(R.drawable.image_2)
                   .into(binding.image)

               binding.title.text = it.title
               binding.imdb.text = it.language
               binding.description.text = it.plot
           }
        })

        viewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideProgress()
            activity?.toast(it)
        })
    }


}
