package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.giveawayapp.controllers.ProfileActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityProfileBinding
    private lateinit var controller: ProfileActivityController
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

        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("USER_ID", -1)

        val userDao = AppDatabase.getDatabase(this).userDao()

        controller = ProfileActivityController(userDao)

        getUserInfo(userId)

        with(binding) {

            setUpBottomNavigation(bottomNavigation, R.id.navigation_profile)

            buttonLogout.setOnClickListener {

                logOutUser()

            }
        }
    }

    private fun getUserInfo(userId: Int)
    {
        lifecycleScope.launch {

            val user = controller.getUserById(userId)

            if (user != null)
            {

                with(binding) {

                    textViewUsername.text = user.username
                    textViewEmail.text = user.email
                }
            }
        }
    }

    private fun logOutUser()
    {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        sharedPreferences.edit()
            .remove("USER_ID")
            .apply()
    }
}