package com.example.giveawayapp.controllers

import android.util.Log
import com.example.giveawayapp.data.ItemDAO
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.models.ItemCategory
import java.util.Date

class MyItemsActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun addItem(
        title: String,
        itemCategory: String?,
        description: String,
        location: String,
        imageUrl: String,
        userId: Int,
    ): Boolean
    {
        return try
        {
            val categoryToEnum =
                ItemCategory.valueOf(itemCategory ?: "Other")

            val dateCreated = Date()

            val newItem = Item(
                title = title,
                itemCategory = categoryToEnum,
                description = description,
                location = location,
                userId = userId,
                imageUrl = imageUrl,
                dateCreated = dateCreated
            )
            itemDAO.insert(newItem)
            Log.d(TAG, "AddItem:success")
            true
        }
        catch (e: Exception)
        {
            Log.e(TAG, "AddItem: Failure", e)
            false
        }
    }

    suspend fun updateItem(
        item: Item?,
        title: String,
        itemCategory: String?,
        description: String,
        location: String,
        imageUrl: String
    ): Boolean
    {
        return try
        {
            val categoryToEnum =
                ItemCategory.valueOf(itemCategory ?: "Other")

            val updatedItem = item?.copy(
                title = title,
                itemCategory = categoryToEnum,
                description = description,
                location = location,
                imageUrl = imageUrl
            )

            if (updatedItem != null)
            {
                itemDAO.update(updatedItem)
            }

            Log.d(TAG, "updateItem:success")
            true

        }
        catch (e: Exception)
        {
            Log.e(TAG, "updateItem:failed")
            false
        }
    }

    suspend fun deleteItem(item: Item?): Boolean
    {
        return try
        {
            if (item != null)
            {
                itemDAO.delete(item)
                Log.d(TAG, "deleteItem:success")
            }
            true
        }
        catch (e: Exception)
        {
            Log.e(TAG, "deleteItem:failure", e)
            false
        }
    }

    suspend fun getItemById(itemId: Int): Item?
    {
        return try
        {
            val item = itemDAO.getItem(itemId)
            Log.d(TAG, "getItemById:success")
            item
        }
        catch (e: Exception)
        {
            Log.d(TAG, "getItemBYId:failure")
            null
        }
    }

    companion object
    {
        private const val TAG = "MyItemsActivityController"
    }
}