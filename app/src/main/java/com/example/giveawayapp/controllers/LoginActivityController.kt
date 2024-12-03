package com.example.giveawayapp.controllers

import android.content.SharedPreferences
import com.example.giveawayapp.data.UserDAO
import com.example.giveawayapp.utils.PasswordUtils

class LoginActivityController(
    private val userDAO: UserDAO,
    private val sharedPreferences: SharedPreferences
)
{
    suspend fun loginUser(email: String, password: String): Boolean
    {
        val user = userDAO.getEmail(email) ?: return false

        val hashedPassword = PasswordUtils.hashPassword(password)
        
        return if (hashedPassword == user.password)
        {
            sharedPreferences.edit()
                .putInt("USER_ID", user.userId)
                .apply()
            true
        }
        else
        {
            false
        }
    }
}