package com.example.myawesomekotlinproject
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


// We only need to access the DAO, so we pass the DAO instead of the
// whole database to the Word Repository
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // The Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // By default, Room runs suspended queries off the main thread,
    // so we do not implement anything extra to ensure that db code
    // is not running off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}