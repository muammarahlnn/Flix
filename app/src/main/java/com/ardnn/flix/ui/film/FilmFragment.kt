package com.ardnn.flix.ui.film

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ardnn.flix.api.response.Movie
import com.ardnn.flix.api.response.TvShow
import com.ardnn.flix.databinding.FragmentFilmBinding
import com.ardnn.flix.ui.movie_detail.MovieDetailActivity
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailActivity
import com.ardnn.flix.utils.FilmClickListener

class FilmFragment : Fragment(), FilmClickListener {

    private lateinit var viewModel: FilmViewModel
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

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

        // get section and set it as parameter on view model
        val section = arguments?.getInt(ARG_SECTION, 0) as Int
        viewModel = ViewModelProvider(this, FilmViewModelFactory(section))[FilmViewModel::class.java]

        // subscribe view model
        subscribe(section)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribe(section: Int) {
        setFilmList(section)
    }

    private fun setFilmList(section: Int) {
        when (section) {
            0 -> { // movies
                viewModel.movieList.observe(viewLifecycleOwner, { movieList ->
                    val adapter = MovieAdapter(movieList, this)
                    binding.rvFilm.adapter = adapter
                })
            }
            1 -> { // tv shows
                viewModel.tvShowList.observe(viewLifecycleOwner, { tvShowList ->
                    val adapter = TvShowAdapter(tvShowList, this)
                    binding.rvFilm.adapter = adapter
                })
            }
        }
    }

    override fun onMovieClicked(movie: Movie) {
        // to movie detail
        val toMovieDetail = Intent(activity, MovieDetailActivity::class.java)
        toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
        startActivity(toMovieDetail)
    }

    override fun onTvShowClicked(tvShow: TvShow) {
        // to tv show detail
        val toTvShowDetail = Intent(activity, TvShowDetailActivity::class.java)
        toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShow.id)
        startActivity(toTvShowDetail)
    }

}