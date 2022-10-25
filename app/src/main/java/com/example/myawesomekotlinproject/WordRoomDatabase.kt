package com.example.myawesomekotlinproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    // This abstract DAO getter method exposes the DAO
    abstract fun wordDao(): WordDao

    companion object {
        // We should always have only a single instance of the Database class.
        // So this companion object implements the Singleton Pattern.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase {

            // If INSTANCE is null, create the database class.
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()

                // Set the newly created instance.
                INSTANCE = instance

                // Return the newly created instance.
                instance
            }
        }
    }
}