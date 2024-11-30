package com.example.giveawayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.giveawayapp.databinding.ActivityProfileBinding

class ProfileActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            val sharedPreferences = getSharedPreferences(
                "user_prefs",
                MODE_PRIVATE
            )
            setUpBottomNavigation(bottomNavigation, R.id.navigation_profile, sharedPreferences)
        }
    }
}