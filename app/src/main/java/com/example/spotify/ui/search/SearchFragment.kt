package com.example.spotify.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spotify.R
import com.example.spotify.SpotifyViewModel
import com.example.spotify.databinding.FragmentSearchBinding
import com.example.spotify.model.local.TrackEntity
import com.example.spotify.model.remote.AlbumItem
import com.example.spotify.model.remote.ArtistItem
import com.example.spotify.model.remote.PlaylistItem
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.trackLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                trackAdapter.list.clear()
                trackAdapter.list.add(Headers.TRACK)
                trackAdapter.list.addAll(it)
                trackAdapter.notifyDataSetChanged()
            }
        }





//        viewModel.searchResult.observe(viewLifecycleOwner) {
//            binding.loading.visibility = View.VISIBLE
//            it.data?.let { spotifyResponse ->
//                adapter.list.clear()
//                val tracks = spotifyResponse.tracks.items
//                if (tracks.isNotEmpty()) {
//                    adapter.list.add(Headers.TRACK)
//                    adapter.list.addAll(tracks)
//                }
//
//                val albums = spotifyResponse.albums.items
//                if (albums.isNotEmpty()) {
//                    adapter.list.add(Headers.ALBUM)
//                    adapter.list.addAll(albums)
//                }
//
//                val playlists = spotifyResponse.playlists.items
//                if (albums.isNotEmpty()) {
//                    adapter.list.add(Headers.PLAYLIST)
//                    adapter.list.addAll(playlists)
//                }
//
//                val artists = spotifyResponse.artists.items
//                if (artists.isNotEmpty()) {
//                    adapter.list.add(Headers.ARTIST)
//                    adapter.list.addAll(artists)
//                }
//                adapter.notifyDataSetChanged()
//                binding.loading.visibility = View.GONE
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onClick(trackItem: TrackEntity) {
//        viewModel.trackResult.postValue(Resource.Success(trackItem))
        findNavController().navigate(R.id.action_searchFragment_to_trackDetailFragment)
    }

    override fun onClick(trackItem: AlbumItem) {
        TODO("Not yet implemented")
    }

    override fun onClick(trackItem: PlaylistItem) {
        TODO("Not yet implemented")
    }

    override fun onClick(trackItem: ArtistItem) {
        TODO("Not yet implemented")
    }
}
enum class Headers {
    ALBUM, TRACK, PLAYLIST, ARTIST
}