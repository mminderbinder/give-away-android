package com.example.giveawayapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.giveawayapp.models.Item

@Dao
interface ItemDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items WHERE userId != :id")
    suspend fun getItemsOfOtherUsers(id: Int): List<Item>

    @Query("SELECT * FROM items WHERE userId = :id")
    suspend fun getUserItems(id: Int): List<Item>

}