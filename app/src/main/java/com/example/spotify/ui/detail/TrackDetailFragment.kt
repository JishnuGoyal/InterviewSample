package com.example.spotify.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotify.R
import com.example.spotify.SpotifyViewModel
import com.example.spotify.databinding.FragmentSearchBinding
import com.example.spotify.databinding.FragmentTrackDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackDetailFragment : Fragment() {
    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentTrackDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.trackResult.observe(viewLifecycleOwner) {
            it.data?.let { track ->
                binding.progressBar.progress = track.popularity
                binding.titleTextView.text = track.name
                binding.descriptionTextView.text = track.listOfArtists()
                binding.albumArtImageView.load(track.album.images.first().url)
            }
        }

//        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(false) {
//            override fun handleOnBackPressed() {
//                requireActivity().supportFragmentManager.popBackStack()
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrackDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "track detail fragment"
        @JvmStatic
        fun newInstance() =
            TrackDetailFragment().apply {

            }
    }
}