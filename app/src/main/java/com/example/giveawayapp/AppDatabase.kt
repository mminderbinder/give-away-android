package com.example.giveawayapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.giveawayapp.dao.ItemDAO
import com.example.giveawayapp.dao.UserDAO
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.models.User

@Database(
    entities = [User::class, Item::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun itemDao(): ItemDAO
    
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
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