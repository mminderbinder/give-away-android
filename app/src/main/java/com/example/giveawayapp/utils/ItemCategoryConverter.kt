package com.example.giveawayapp.utils

import androidx.room.TypeConverter
import com.example.giveawayapp.models.ItemCategory

class ItemCategoryConverter
{
    @TypeConverter
    fun fromCategory(itemCategory: ItemCategory): String
    {
        return itemCategory.name
    }

    @TypeConverter
    fun toCategory(itemCategory: String): ItemCategory
    {
        return ItemCategory.valueOf(itemCategory)
    }
}