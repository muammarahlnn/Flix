package com.ardnn.flix.ui.film

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.databinding.FragmentFilmBinding
import com.ardnn.flix.ui.movie_detail.MovieDetailActivity
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailActivity
import com.ardnn.flix.utils.FilmClickListener
import com.ardnn.flix.viewmodel.ViewModelFactory

class FilmFragment : Fragment(), FilmClickListener, View.OnClickListener {

    private lateinit var viewModel: FilmViewModel
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    private val page = 1 // default page to fetch movies and tv shows
    private var section = 0

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(index: Int) =
            FilmFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFilmBinding.inflate(inflater, container, false)

        // set recyclerview
        binding.rvFilm.layoutManager = GridLayoutManager(requireActivity(), 2)

        // initialize view model
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[FilmViewModel::class.java]

        // get section and set it on view model
        section = arguments?.getInt(ARG_SECTION, 0) as Int
        viewModel.setSection(section)

        // subscribe view model
        subscribe(section)

        // click listeners
        binding.btnRefresh.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRefresh -> {
                // not fix yet
                Toast.makeText(activity, "refresh", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun subscribe(section: Int) {
        viewModel.getIsLoading().observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })

        viewModel.getIsLoadFailure().observe(viewLifecycleOwner, { isFailure ->
            showAlert(isFailure)
        })

        setFilmList(section)
    }

    private fun setFilmList(section: Int) {
        when (section) {
            0 -> { // movies
                viewModel.getMovies(page).observe(viewLifecycleOwner, { movieList ->
                    val adapter = MovieAdapter(movieList, this)
                    binding.rvFilm.adapter = adapter
                })
            }
            1 -> { // tv shows
                viewModel.getTvShows(page).observe(viewLifecycleOwner, { tvShowList ->
                    val adapter = TvShowAdapter(tvShowList, this)
                    binding.rvFilm.adapter = adapter
                })
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showAlert(isFailure: Boolean) {
        binding.llAlert.visibility = if (isFailure) View.VISIBLE else View.GONE
    }

    override fun onMovieClicked(movie: MovieEntity) {
        // to movie detail
        val toMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
        startActivity(toMovieDetail)
    }

    override fun onTvShowClicked(tvShow: TvShowEntity) {
        // to tv show detail
        val toTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShow.id)
        startActivity(toTvShowDetail)
    }

}