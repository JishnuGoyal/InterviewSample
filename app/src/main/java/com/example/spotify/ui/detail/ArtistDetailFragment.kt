package com.example.spotify.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotify.R
import com.example.spotify.databinding.FragmentArtistDetailBinding
import com.example.spotify.ui.search.SpotifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailFragment : Fragment() {
    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentArtistDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentArtist?.let { artist ->
            binding.titleTextView.text = artist.name
            binding.progressBar.progress = artist.popularity
            binding.titleTextView.text = artist.name
            binding.descriptionTextView.text = artist.genres
            binding.subheadingTextView.text = artist.followers.toString() + " Followers"
            binding.albumArtImageView.load(artist.thumbnailUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArtistDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
