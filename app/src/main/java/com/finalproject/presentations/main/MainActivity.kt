package com.finalproject.presentations.main

import android.os.Bundle
import android.text.TextUtils.isEmpty
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
                R.id.homeAdminHCFragment -> {
                    showBottomNavAdminHC()
                }
                R.id.homeEmployeeFragment -> {
                    showBottomNavEmployee()
                    binding.bottomNavigationView.menu.getItem(0).isEnabled = false
                    binding.bottomNavigationView.menu.getItem(1).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(2).isEnabled = true
                }
                R.id.historyOnProgressFragment, R.id.historySuccessFragment2 -> {
                    binding.bottomNavigationView.menu.getItem(0).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(1).isEnabled = false
                    binding.bottomNavigationView.menu.getItem(2).isEnabled = true
                }
                R.id.accountFragment -> {
                    binding.bottomNavigationView.menu.getItem(0).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(1).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(2).isEnabled = false
                }
                R.id.claimGlassesFragment -> hideBottomNav()
                R.id.claimInsuranceFragment -> hideBottomNav()
                R.id.claimPregnantFragment -> hideBottomNav()
                R.id.claimSPDFragment -> hideBottomNav()
                R.id.claimTrainingFragment -> hideBottomNav()
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
            }
            visibility = View.VISIBLE
        }
    }

    //Function untuk menyembunyikan bottom Navigation View
    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }



}