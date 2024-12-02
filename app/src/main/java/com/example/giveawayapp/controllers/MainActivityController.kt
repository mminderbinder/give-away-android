package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.ItemDAO
import com.example.giveawayapp.models.Item

class MainActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun getUserItems(userId: Int): List<Item>?
    {
        return try
        {
            val itemList = itemDAO.getUserItems(userId)
            Log.d(TAG, "getUserItems:success")
            itemList
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getUserItems: failed to get items for user with $userId", e)
            null
        }
    }

    companion object
    {
        private const val TAG = "MainActivityController"
    }
}