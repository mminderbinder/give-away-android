package com.example.giveawayapp.controllers

import android.content.SharedPreferences
import com.example.giveawayapp.data.UserDAO
import java.security.MessageDigest

class LoginActivityController(
    private val userDAO: UserDAO,
    private val sharedPreferences: SharedPreferences
)
{
    suspend fun loginUser(email: String, password: String): Boolean
    {
        val user = userDAO.getEmail(email) ?: return false
        val hashedPassword = hashPassword(password)

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

    private fun hashPassword(password: String): String
    {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}