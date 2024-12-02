package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.ItemDAO
import com.example.giveawayapp.models.Item

class ExpandedItemActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun getSelectedItem(itemId: Int): Item?
    {
        return try
        {

            val selectedItem = itemDAO.getItem(itemId)
            Log.d(TAG, "getSelectedItem:success")
            selectedItem
        }
        catch (e: Exception)
        {
            Log.e(TAG, "getSelectedItem:failed to retrieve item with ID: $itemId", e)
            null
        }
    }

    companion object
    {
        private const val TAG = "ExpandedItemActivityController"
    }

}