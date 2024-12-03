package com.example.giveawayapp.utils

import java.security.MessageDigest

object PasswordUtils
{
    fun hashPassword(password: String): String
    {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}