package com.example.spotify.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotify.R
import com.example.spotify.databinding.FragmentAlbumDetailBinding
import com.example.spotify.ui.search.SpotifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {
    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentAlbumDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentAlbum?.let { album ->
            binding.titleTextView.text = album.name
            binding.descriptionTextView.text = "Released on "+ album.releaseDate
            binding.albumArtImageView.load(album.thumbnailUrl)
            binding.subheadingTextView.text = "by " + album.artists
            binding.popularityTextView.visibility = View.GONE


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlbumDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
