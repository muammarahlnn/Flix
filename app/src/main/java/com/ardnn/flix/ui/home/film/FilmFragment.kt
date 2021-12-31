package com.ardnn.flix.ui.home.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ardnn.flix.databinding.FragmentFilmBinding

class FilmFragment : Fragment() {

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(index: Int) =
            FilmFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION, index)
                }
            }
    }

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFilmBinding.inflate(inflater, container, false)

        // get args
        val section = arguments?.getInt(ARG_SECTION, 0) ?: 0
        when (section) {
            0 -> binding.tvTest.text = "Movies"
            1 -> binding.tvTest.text = "TV Shows"
            else -> binding.tvTest.text = "Else"
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}