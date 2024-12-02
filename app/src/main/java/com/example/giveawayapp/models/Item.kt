package com.example.giveawayapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val itemCategory: ItemCategory,
    val title: String,
    val description: String,
    val userId: Int,
    val location: String,
    val imageUrl: String,
    val status: Boolean = false,
    val dateCreated: Date
)