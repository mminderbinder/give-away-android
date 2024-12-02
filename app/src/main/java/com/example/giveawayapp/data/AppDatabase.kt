package com.example.giveawayapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.models.User
import com.example.giveawayapp.utils.DateConverter
import com.example.giveawayapp.utils.ItemCategoryConverter

@Database(
    entities = [User::class, Item::class],
    version = 8
)
@TypeConverters(ItemCategoryConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDAO
    abstract fun itemDao(): ItemDAO

    companion object
    {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase
        {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "give_away_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}