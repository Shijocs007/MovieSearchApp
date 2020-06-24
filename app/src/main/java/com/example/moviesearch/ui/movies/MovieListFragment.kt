package com.example.moviesearch.ui.movies


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesearch.R
import com.example.moviesearch.adapter.MoviesAdapter
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.databinding.FragmentMovieListBinding
import com.example.moviesearch.listerners.IAdapterListener
import com.example.moviesearch.utils.*
import com.example.moviesearch.viewmodel.MoviesViewModel
import com.example.moviesearch.viewmodel.MoviesViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment(), KodeinAware, IAdapterListener {

    lateinit var binding: FragmentMovieListBinding
    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()

    private val factory : MoviesViewModelFactory by instance()
    private var mMoviesAdapter: MoviesAdapter? = null
    private lateinit var viewModel: MoviesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, factory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMovieListBinding>(inflater, R.layout.fragment_movie_list, container, false)
        binding.viewmodel = viewModel

        initRecyclerview()

        binding.serchview.afterTextChangedDelayed {
            it.let {
                if(it.length >= 3) {
                    binding.progressBar.showProgress()
                    viewModel.getMoviesList(it)
                }
            }
        }
        return binding.root
    }

    private fun initRecyclerview() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(
            SpacingItemDecoration(
                2,
                Utils.dpToPx(context!!, 4),
                true
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMiviesLivedata().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideProgress()
            if(it != null && it.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noItemLayout.visibility = View.GONE
                mMoviesAdapter = MoviesAdapter(context!!, it, this)
                binding.recyclerView.adapter = mMoviesAdapter
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.noItemLayout.visibility = View.VISIBLE
            }
        })

        viewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideProgress()
            activity?.toast(it)
        })
    }

    override fun onItemClicked(movie: Movie) {
        viewModel.gotoDetailsFragment(movie.title)
    }
}
