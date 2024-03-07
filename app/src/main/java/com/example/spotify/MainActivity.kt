package com.example.spotify

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.spotify.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: SpotifyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToSearchFragment()

    }

    private fun navigateToSearchFragment() {
//        val fragment = supportFragmentManager.findFragmentByTag(SearchFragment.TAG)
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_view, fragment ?: SearchFragment.newInstance())
//            .addToBackStack(SearchFragment.TAG)
//            .commit()
    }
}