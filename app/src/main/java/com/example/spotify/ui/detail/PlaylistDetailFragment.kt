package com.example.spotify.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotify.R
import com.example.spotify.databinding.FragmentPlaylistDetailBinding
import com.example.spotify.ui.search.SpotifyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {
    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentPlaylistDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentPlaylist?.let { playlist ->
            binding.titleTextView.text = playlist.name
            binding.descriptionTextView.text = ""+ playlist.description
            binding.albumArtImageView.load(playlist.thumbnailUrl)
            binding.subheadingTextView.text = "by " + playlist.owner
            binding.popularityTextView.text = "Total tracks: " + playlist.numberOfTracks
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}