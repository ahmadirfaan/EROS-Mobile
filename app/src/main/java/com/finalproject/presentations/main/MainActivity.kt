package com.finalproject.presentations.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finalproject.R
import com.finalproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        setupNav()

    }

    private fun setupNav() {
        navController.addOnDestinationChangedListener { _, destination, _ -> //Untuk membuat settingan hide atau show bottomnavigation view pada layout activity
            when (destination.id) {
                R.id.splashFragment -> hideBottomNav()
                R.id.loginFragment -> hideBottomNav()
                R.id.homeAdminHCFragment -> showBottomNavAdminHC()
                R.id.homeEmployeeFragment -> {
                    showBottomNavEmployee()
                }
                R.id.claimGlassesFragment -> hideBottomNav()
            }
        }

    }

    //Function untuk memunculkan bottom Navigation View
    private fun showBottomNavEmployee() {
        binding.bottomNavigationView.apply {
            if (menu.isEmpty()) {
                inflateMenu(R.menu.bottom_navigation_menu_user)
                setupWithNavController(navController)
            }
            visibility = View.VISIBLE
        }
    }

    private fun showBottomNavAdminHC() {
        binding.bottomNavigationView.apply {
            if (menu.isEmpty()) {
                inflateMenu(R.menu.bottom_navigation_menu_admin_hc)
                setupWithNavController(navController)
            } else if (menu.isNotEmpty()) {
                visibility = View.VISIBLE
            }
        }
    }

    //Function untuk menyembunyikan bottom Navigation View
    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

}