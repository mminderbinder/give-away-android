package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.UserDAO
import com.example.giveawayapp.models.User

class ProfileActivityController(
    private val userDAO: UserDAO
)
{
    suspend fun updateUserEmail(email: String)
    {


    }

    suspend fun updateUserPassword(password: String)
    {


    }


    suspend fun getUserById(userId: Int): User?
    {

        return try
        {
            val user = userDAO.getUser(userId)
            Log.d(TAG, "getUserById:success")
            user
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getUserById:failure")
            null
        }
    }

    companion object
    {
        private const val TAG = "ProfileActivityController"
    }
}