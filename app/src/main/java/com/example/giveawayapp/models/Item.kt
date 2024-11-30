package com.example.giveawayapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val itemCategory: ItemCategory,
    val title: String,
    val description: String,
    val userId: Int,
    val imageUrl: String = "",
    val location: String,
    val status: Boolean = false,
)
