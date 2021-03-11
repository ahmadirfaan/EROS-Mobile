package com.finalproject.presentations.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finalproject.R
import com.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        setupNav()

    }

    private fun setupNav() {
        navController.addOnDestinationChangedListener { _, destination, _ -> //Untuk membuat settingan hide atau show bottomnavigation view pada layout activity
            when (destination.id) {
                R.id.splashFragment -> hideBottomNav()
                R.id.loginFragment -> hideBottomNav()
            }
        }

    }

    //Function untuk memunculkan bottom Navigation View
    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE

    }

    //Function untuk menyembunyikan bottom Navigation View
    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE

    }

}