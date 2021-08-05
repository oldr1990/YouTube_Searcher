package com.github.youtube_searcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.github.youtube_searcher.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item_youtubeFragment -> {
                   if (navController.currentDestination?.id != R.layout.fragment_youtube)
                    navController.popBackStack()
                    true
                }
                R.id.menu_item_playlistFragment -> {
                    if (navController.currentDestination?.id != R.layout.fragment_playlist)
                    navController.navigate(R.id.action_youtubeFragment_to_playlistFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}