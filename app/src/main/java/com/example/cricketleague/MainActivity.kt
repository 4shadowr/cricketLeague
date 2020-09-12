package com.example.cricketleague

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.cricketleague.services.MockService
import com.example.cricketleague.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mockService: MockService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mockService = MockService.instance
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        val bottomNav = binding.bottomNavigationView
        val abc = AppBarConfiguration.Builder(TOP_LEVEL_DESTINATIONS).build()
        val toolbar: Toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        NavigationUI.setupWithNavController(bottomNav, navController)
        NavigationUI.setupWithNavController(toolbar, navController, abc)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu,menu)

        return true
    }

    companion object {
        private const val TAG = "MainActivity"
        private val TOP_LEVEL_DESTINATIONS = setOf(R.id.nav_home,R.id.nav_teams,R.id.nav_players,R.id.nav_matches)
    }
}