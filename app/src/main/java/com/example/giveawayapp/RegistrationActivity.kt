package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.giveawayapp.controllers.RegistrationActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var registrationActivityController: RegistrationActivityController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userDAO = AppDatabase.getDatabase(this).userDao()
        registrationActivityController = RegistrationActivityController(userDAO)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            buttonRegister.setOnClickListener {

                val username = editTextUsername.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                val confirmPassword = editTextConfirmPassword.text.toString().trim()

                registerUser(
                    username = username,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            }
        }
    }

    private fun registerUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    )
    {
        lifecycleScope.launch {

            if (!validateInputs(username, email, password, confirmPassword))
            {
                return@launch
            }

            val success = registrationActivityController.registerUser(
                username = username,
                email = email,
                password = password
            )
            if (success)
            {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Registration successful!",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Registration failed. Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun validateInputs(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean
    {
        return when
        {
            username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
            {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please fill out all fields",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            password != confirmPassword ->
            {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            registrationActivityController.isEmailTaken(email) ->
            {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Email is already registered",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            registrationActivityController.isUsernameTaken(username) ->
            {
                Toast.makeText(this@RegistrationActivity, "Username is taken", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> true
        }
    }
}