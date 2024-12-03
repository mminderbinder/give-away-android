package com.example.giveawayapp.controllers

import android.content.SharedPreferences
import android.util.Log
import com.example.giveawayapp.data.UserDAO
import com.example.giveawayapp.models.User
import com.example.giveawayapp.utils.PasswordUtils

class RegistrationActivityController(
    private val userDAO: UserDAO,
    private val sharedPreferences: SharedPreferences
)
{
    suspend fun registerUser(username: String, email: String, password: String): Boolean
    {
        return try
        {
            val hashedPassword = PasswordUtils.hashPassword(password)

            val newUser = User(
                username = username,
                email = email,
                password = hashedPassword
            )

            val newUserId = userDAO.insert(newUser).toInt()

            sharedPreferences.edit()
                .putInt("USER_ID", newUserId)
                .apply()

            Log.d(TAG, "registerUser:success")
            true
        }
        catch (e: Exception)
        {
            Log.e(TAG, "RegisterUser:failure", e)
            false
        }
    }

    suspend fun isEmailTaken(email: String): Boolean
    {
        val user = userDAO.getEmail(email)
        return user != null
    }

    suspend fun isUsernameTaken(username: String): Boolean
    {
        val user = userDAO.getUsername(username)
        return user != null
    }

    companion object
    {
        private const val TAG = "RegistrationActivityController"
    }
}