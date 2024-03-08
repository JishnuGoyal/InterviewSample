package com.example.spotify.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotify.ui.search.SpotifyViewModel
import com.example.spotify.databinding.FragmentTrackDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackDetailFragment : Fragment() {
    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentTrackDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentTrack?.let { track ->
            binding.progressBar.progress = track.popularity
            binding.titleTextView.text = track.name
            binding.descriptionTextView.text = track.artists
            binding.albumArtImageView.load(track.thumbnailUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrackDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}