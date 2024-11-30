package com.example.giveawayapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.giveawayapp.controllers.LoginActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginActivityController: LoginActivityController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userDao = AppDatabase.getDatabase(this).userDao()

        // TODO: Centralize initialization
        sharedPreferences =
            getSharedPreferences("user_prefs", MODE_PRIVATE)

        loginActivityController = LoginActivityController(userDao, sharedPreferences)
        isUserLoggedIn()

        with(binding) {
            buttonLogin.setOnClickListener {

                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                loginUser(email, password)
            }

            textViewRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }
        }
    }

    private fun loginUser(email: String, password: String)
    {

        lifecycleScope.launch {
            if (!validateInputs(email, password))
            {
                return@launch
            }

            val success = loginActivityController.loginUser(email, password)
            if (success)
            {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            else
            {
                Toast.makeText(
                    this@LoginActivity,
                    "Login failed. Check your credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean
    {
        return when
        {
            email.isBlank() || password.isBlank() ->
            {
                Toast.makeText(this@LoginActivity, "Please fill out all fields", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> true
        }
    }

    private fun isUserLoggedIn()
    {
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1)
        {
            startActivity(Intent(this, MainActivity::class.java))
            return
        }
    }
}