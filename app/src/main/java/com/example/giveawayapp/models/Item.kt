package com.example.giveawayapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val title: String,
    val description: String,
    val itemType: ItemType,
    val imageUrl: String,
    val location: String,
    val status: Boolean
)
