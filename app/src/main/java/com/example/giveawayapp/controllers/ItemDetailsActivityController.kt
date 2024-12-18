package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.ItemDAO
import com.example.giveawayapp.models.Item

class ItemDetailsActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun getItemList(userId: Int): List<Item>?
    {
        return try
        {
            val itemList = itemDAO.getItemsOfOtherUsers(userId)
            Log.d(TAG, "getItemList:success")
            itemList
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getItemList: failed to get item list for user with userId: $userId", e)
            null
        }
    }

    companion object
    {
        private const val TAG = "ItemDetailsActivityController"
    }
}