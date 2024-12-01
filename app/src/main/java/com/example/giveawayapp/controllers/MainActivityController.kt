package com.example.giveawayapp.controllers

import com.example.giveawayapp.data.ItemDAO

class MainActivityController(
    private val itemDAO: ItemDAO
)
{

    companion object
    {
        private const val TAG = "MainActivityController"
    }
}