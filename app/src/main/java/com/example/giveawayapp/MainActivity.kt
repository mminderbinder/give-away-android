package com.example.giveawayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.giveawayapp.databinding.ActivityMainBinding

class MainActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {

            // TODO: Centralize initialization
            val sharedPreferences = getSharedPreferences(
                "user_prefs",
                MODE_PRIVATE
            )

            setUpBottomNavigation(bottomNavigation, R.id.navigation_home, sharedPreferences)
        }
    }
}