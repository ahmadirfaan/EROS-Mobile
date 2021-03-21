package com.finalproject.presentations.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finalproject.R
import com.finalproject.databinding.ActivityMainBinding
import com.finalproject.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
//    private val REQ_CAMERA = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        setupNav()
        checkForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "Penyimpanan", AppConstant.STORAGE_READ_PERMISSION_CODE)
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
                    showBottomNavEmployee()
                    binding.bottomNavigationView.menu.getItem(0).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(1).isEnabled = false
                    binding.bottomNavigationView.menu.getItem(2).isEnabled = true
                }
                R.id.accountFragment -> {
                    showBottomNavEmployee()
                    binding.bottomNavigationView.menu.getItem(0).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(1).isEnabled = true
                    binding.bottomNavigationView.menu.getItem(2).isEnabled = false
                }
                R.id.claimGlassesFragment -> hideBottomNav()
                R.id.claimInsuranceFragment -> hideBottomNav()
                R.id.claimPregnantFragment -> hideBottomNav()
                R.id.claimSPDFragment -> hideBottomNav()
                R.id.claimTrainingFragment -> hideBottomNav()
                R.id.changePasswordFragment -> hideBottomNav()
                R.id.detailReimbursementFragment -> hideBottomNav()
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

    private fun checkForPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
//                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
//                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
//                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name Tidak Diberikan, maka aplikasi berhenti", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "$name Diberikan", Toast.LENGTH_SHORT).show()
            }
        }
        when (requestCode) {
            AppConstant.STORAGE_READ_PERMISSION_CODE -> innerCheck("Akses izin ke Penyimpanan")
//            AppConstant.STORAGE_WRITE_PERMISSION_CODE -> innerCheck("Write Storage")
//            REQ_CAMERA -> innerCheck("CAMERA")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Untuk Menggunakan Aplikasi dibutuhkan ijin ke $name")
            setTitle("Membutuhkan Ijin")
            setPositiveButton("Ok") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


}