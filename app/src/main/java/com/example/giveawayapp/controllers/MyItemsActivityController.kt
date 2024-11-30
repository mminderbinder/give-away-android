package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.ItemDAO
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.models.ItemCategory
import java.util.Locale

class MyItemsActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun addItem(
        title: String,
        itemCategory: String?,
        description: String,
        location: String,
        userId: Int,
    ): Boolean
    {
        return try
        {
            val categoryToEnum =
                ItemCategory.valueOf(itemCategory?.uppercase(Locale.ROOT) ?: "OTHER")

            val newItem = Item(
                title = title,
                itemCategory = categoryToEnum,
                description = description,
                location = location,
                userId = userId
            )
            itemDAO.insert(newItem)
            Log.d(TAG, "AddItem:success")
            println("New item created: title: $title, itemCategory: $itemCategory, description: $description, location: $location, userId: $userId")
            true
        }
        catch (e: Exception)
        {
            Log.e(TAG, "AddItem: Failure", e)
            false
        }
    }

    companion object
    {
        private const val TAG = "MyItemsActivityController"
    }
}