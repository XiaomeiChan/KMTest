package com.example.kmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kmtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)
        if (navHostFragment is NavHostFragment) {
            navController = navHostFragment.navController
        }
    }
}