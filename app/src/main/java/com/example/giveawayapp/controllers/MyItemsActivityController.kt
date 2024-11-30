package com.example.giveawayapp.controllers

import com.example.giveawayapp.data.ItemDAO

class MyItemsActivityController(
    private val itemDAO: ItemDAO
)
{
    suspend fun AddItem(
        userId: String,
        title: String,
        description: String,
        location: String
    )
    {

    }
}