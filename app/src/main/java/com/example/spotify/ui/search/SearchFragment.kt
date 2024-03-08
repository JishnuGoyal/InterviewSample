package com.example.spotify.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spotify.R
import com.example.spotify.databinding.FragmentSearchBinding
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SEARCH_REQUEST_DELAY_TIME = 500L
@AndroidEntryPoint
class SearchFragment : Fragment(), SearchResultRecyclerAdapter.SearchResultAdapterListener {

    private val viewModel: SpotifyViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var trackAdapter: SearchResultRecyclerAdapter
    private lateinit var albumAdapter: SearchResultRecyclerAdapter
    private lateinit var playlistAdapter: SearchResultRecyclerAdapter
    private lateinit var artistAdapter: SearchResultRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // don't call the api immediately.
        var job: Job? = null
        binding.searchInputText.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_REQUEST_DELAY_TIME)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.search(editable.toString())
                    }
                }
            }
        }

        trackAdapter = SearchResultRecyclerAdapter(this)
        binding.trackRecyclerView.adapter = trackAdapter

        albumAdapter = SearchResultRecyclerAdapter(this)
        binding.albumRecyclerView.adapter = albumAdapter

        playlistAdapter = SearchResultRecyclerAdapter(this)
        binding.playlistRecyclerView.adapter = playlistAdapter

        artistAdapter = SearchResultRecyclerAdapter(this)
        binding.artistRecyclerView.adapter = artistAdapter

        viewModel.trackLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                trackAdapter.list.clear()
                trackAdapter.list.add(Headers.TRACK)
                trackAdapter.list.addAll(it)
                trackAdapter.notifyDataSetChanged()
            }
        }

        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                albumAdapter.list.clear()
                albumAdapter.list.add(Headers.ALBUM)
                albumAdapter.list.addAll(it)
                albumAdapter.notifyDataSetChanged()
            }
        }

        viewModel.playlistLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                playlistAdapter.list.clear()
                playlistAdapter.list.add(Headers.PLAYLIST)
                playlistAdapter.list.addAll(it)
                playlistAdapter.notifyDataSetChanged()
            }
        }

        viewModel.artistLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                artistAdapter.list.clear()
                artistAdapter.list.add(Headers.ARTIST)
                artistAdapter.list.addAll(it)
                artistAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onClick(albumItem: AlbumEntity) {
        viewModel.currentAlbum = albumItem
        findNavController().navigate(R.id.action_searchFragment_to_trackDetailFragment)
    }

    override fun onClick(playlistItem: PlaylistEntity) {
        viewModel.currentPlaylist = playlistItem
        findNavController().navigate(R.id.action_searchFragment_to_trackDetailFragment)
    }

    override fun onClick(artistItem: ArtistEntity) {
        viewModel.currentArtist = artistItem
        findNavController().navigate(R.id.action_searchFragment_to_trackDetailFragment)
    }

    override fun onClick(trackItem: TrackEntity) {
        viewModel.currentTrack = trackItem
        findNavController().navigate(R.id.action_searchFragment_to_trackDetailFragment)
    }
}
enum class Headers {
    ALBUM, TRACK, PLAYLIST, ARTIST
}