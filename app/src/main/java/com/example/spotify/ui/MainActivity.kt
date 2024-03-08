package com.example.spotify.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.spotify.databinding.ActivityMainBinding
import com.example.spotify.ui.search.SpotifyViewModel
import com.example.spotify.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SpotifyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        viewModel.searchResult.observe(this) {
            if (it is Resource.Error) {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = it.message
            }
            if (it is Resource.Loading) {
                binding.loading.visibility = View.VISIBLE
            } else if (it is Resource.Success) {
                binding.loading.visibility = View.GONE
            }
        }
    }
}