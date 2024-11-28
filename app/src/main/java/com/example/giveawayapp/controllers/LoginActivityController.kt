package com.example.giveawayapp.controllers

import com.example.giveawayapp.data.UserDAO
import java.security.MessageDigest

class LoginActivityController(
    private val userDAO: UserDAO
)
{
    suspend fun loginUser(email: String, password: String): Boolean
    {
        val user = userDAO.getEmail(email) ?: return false
        val hashedPassword = hashPassword(password)
        return hashedPassword == user.password
    }

    private fun hashPassword(password: String): String
    {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { "%02x".format(it) }

    }

}