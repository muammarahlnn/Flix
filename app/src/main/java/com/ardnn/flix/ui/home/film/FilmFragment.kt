package com.ardnn.flix.ui.home.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.data.FilmEntity
import com.ardnn.flix.databinding.FragmentFilmBinding
import com.ardnn.flix.utils.ClickListener
import com.ardnn.flix.utils.FilmsData

class FilmFragment : Fragment(), ClickListener<FilmEntity> {

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(index: Int) =
            FilmFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION, index)
                }
            }
    }

    private lateinit var viewModel: FilmViewModel
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFilmBinding.inflate(inflater, container, false)

        // set recyclerview
        with (binding.rvFilm) {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
        }

        // get section and set it as parameter on view model
        val section = arguments?.getInt(ARG_SECTION, 0) ?: 0
        viewModel = ViewModelProvider(this, FilmViewModelFactory(requireContext(), section))[FilmViewModel::class.java]

        // set recyclerview adapter
        viewModel.filmList.observe(viewLifecycleOwner, { filmList ->
            val adapter = FilmAdapter(filmList, this)
            binding.rvFilm.adapter = adapter
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClicked(item: FilmEntity) {
        Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()
    }

}