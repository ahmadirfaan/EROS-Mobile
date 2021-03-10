package com.finalproject.presentations.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.finalproject.R
import com.finalproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}