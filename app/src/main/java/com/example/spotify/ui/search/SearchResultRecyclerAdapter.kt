package com.example.spotify.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spotify.R
import com.example.spotify.databinding.TrackHeaderItemViewBinding
import com.example.spotify.databinding.TrackItemViewBinding
import com.example.spotify.model.remote.Album
import com.example.spotify.model.remote.AlbumItem
import com.example.spotify.model.remote.ArtistItem
import com.example.spotify.model.remote.PlaylistItem
import com.example.spotify.model.remote.TrackItem

class SearchResultRecyclerAdapter(private val listener: SearchResultAdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = ArrayList<Any>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TRACK_VIEW_TYPE -> {
                val binding =
                    TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TrackViewHolder(binding, listener)
            }

            ALBUM_VIEW_TYPE -> {
                val binding =
                    TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TrackViewHolder(binding, listener)
            }

            PLAYLIST_VIEW_TYPE -> {
                val binding =
                    TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TrackViewHolder(binding, listener)
            }
            ARTIST_VIEW_TYPE -> {
                val binding =
                    TrackItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TrackViewHolder(binding, listener)
            }

            else -> {
                val binding =
                    TrackHeaderItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                HeaderViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TRACK_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as TrackItem)
            ALBUM_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as AlbumItem)
            ARTIST_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as ArtistItem)
            PLAYLIST_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as PlaylistItem)
            HEADER_VIEW_TYPE -> (holder as HeaderViewHolder).bind(list[position] as Headers)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is TrackItem -> TRACK_VIEW_TYPE
            is AlbumItem -> ALBUM_VIEW_TYPE
            is PlaylistItem -> PLAYLIST_VIEW_TYPE
            is ArtistItem -> ARTIST_VIEW_TYPE
            Headers.ALBUM -> HEADER_VIEW_TYPE
            Headers.PLAYLIST -> HEADER_VIEW_TYPE
            Headers.TRACK -> HEADER_VIEW_TYPE
            Headers.ARTIST -> HEADER_VIEW_TYPE
            else -> -1
        }
    }

    class TrackViewHolder(private val binding: TrackItemViewBinding, private val listener: SearchResultAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackItem) {
            binding.imageView.load(item.album.images.first().url)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.listOfArtists()

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: AlbumItem) {
            binding.imageView.load(item.images.first().url)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.releaseDate

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: ArtistItem) {
            binding.imageView.load(item.images.first().url)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.genres.toString()

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: PlaylistItem) {
            binding.imageView.load(item.images.first().url)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.owner.displayName

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    class HeaderViewHolder(private val binding: TrackHeaderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Headers) {
            binding.headerTitleTextView.text = when (item) {
                Headers.ALBUM -> "ALBUMS"
                Headers.PLAYLIST -> "PLAYLISTS"
                Headers.TRACK -> "TRACKS"
                Headers.ARTIST -> "ARTISTS"
                else -> "-1"
            }
        }
    }

    companion object {
        const val TRACK_VIEW_TYPE = 1
        const val ARTIST_VIEW_TYPE = 2
        const val PLAYLIST_VIEW_TYPE = 3
        const val ALBUM_VIEW_TYPE = 4
        const val TRACK_HEADER_VIEW_TYPE = 5
        const val ARTIST_HEADER_VIEW_TYPE = 6
        const val PLAYLIST_HEADER_VIEW_TYPE = 7
        const val ALBUM_HEADER_VIEW_TYPE = 8
        const val HEADER_VIEW_TYPE = 9
    }

    interface SearchResultAdapterListener {
        fun onClick(trackItem: TrackItem)
        fun onClick(trackItem: AlbumItem)
        fun onClick(trackItem: PlaylistItem)
        fun onClick(trackItem: ArtistItem)
    }
}

