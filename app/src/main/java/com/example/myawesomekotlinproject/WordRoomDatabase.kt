package com.example.myawesomekotlinproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    // This abstract DAO getter method exposes the DAO
    abstract fun wordDao(): WordDao

    // The following Callback class is attached to the database instance
    // on Creation. In this callback, we override the OnCreate method
    // of the database instance to launch a new coroutine scope
    // and within this scope, populate the database

    // We do this to populate the Database asynchronously
    // on database creation.
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                // Populate synchronously
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        // The actual populate function
        suspend fun populateDatabase(wordDao: WordDao) {
            // Delete all content here.
            wordDao.deleteAll()

            // Add sample words.
            var word = Word(0, "Hello")
            wordDao.insert(word)

            word = Word(0, "World!")
            wordDao.insert(word)

        }
    }

    companion object {
        // We should always have only a single instance of the Database class.
        // So this companion object implements the Singleton Pattern.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        // We pass in a scope parameter, so that we can
        // launch a coroutine from that scope, so that
        // we can populate the database asynchronously
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {

            // If INSTANCE is null, create the database class.
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"

                // Adding a callback to override OnCreate, to populate the
                // database on creation, asynchronously.
                ).addCallback(WordDatabaseCallback(scope)).build()

                // Set the newly created instance.
                INSTANCE = instance

                // Return the newly created instance.
                instance
            }
        }
    }
}