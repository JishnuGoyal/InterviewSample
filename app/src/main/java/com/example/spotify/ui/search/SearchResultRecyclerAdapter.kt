package com.example.spotify.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spotify.R
import com.example.spotify.databinding.TrackHeaderItemViewBinding
import com.example.spotify.databinding.TrackItemViewBinding
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity

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
            TRACK_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as TrackEntity)
            ALBUM_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as AlbumEntity)
            ARTIST_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as ArtistEntity)
            PLAYLIST_VIEW_TYPE -> (holder as TrackViewHolder).bind(list[position] as PlaylistEntity)
            HEADER_VIEW_TYPE -> (holder as HeaderViewHolder).bind(list[position] as Headers)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is TrackEntity -> TRACK_VIEW_TYPE
            is AlbumEntity -> ALBUM_VIEW_TYPE
            is PlaylistEntity -> PLAYLIST_VIEW_TYPE
            is ArtistEntity -> ARTIST_VIEW_TYPE
            Headers.ALBUM -> HEADER_VIEW_TYPE
            Headers.PLAYLIST -> HEADER_VIEW_TYPE
            Headers.TRACK -> HEADER_VIEW_TYPE
            Headers.ARTIST -> HEADER_VIEW_TYPE
            else -> -1
        }
    }

    class TrackViewHolder(private val binding: TrackItemViewBinding, private val listener: SearchResultAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackEntity) {
            binding.imageView.load(item.thumbnailUrl)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.artists

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: AlbumEntity) {
            binding.imageView.load(item.thumbnailUrl)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.releaseDate

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: ArtistEntity) {
            binding.imageView.load(item.thumbnailUrl)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text = item.genres

            binding.root.setOnClickListener {
                listener.onClick(item)
            }
        }

        fun bind(item: PlaylistEntity) {
            binding.imageView.load(item.thumbnailUrl)
            binding.titleTextView.text = item.name
            binding.descriptionTextView.text =
                binding.root.context.getString(R.string.owner_info, item.owner)

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
            }
        }
    }

    companion object {
        const val TRACK_VIEW_TYPE = 1
        const val ARTIST_VIEW_TYPE = 2
        const val PLAYLIST_VIEW_TYPE = 3
        const val ALBUM_VIEW_TYPE = 4
        const val HEADER_VIEW_TYPE = 5
    }

    interface SearchResultAdapterListener {
        fun onClick(albumItem: AlbumEntity)
        fun onClick(playlistItem: PlaylistEntity)
        fun onClick(artistItem: ArtistEntity)
        fun onClick(trackItem: TrackEntity)
    }
}

