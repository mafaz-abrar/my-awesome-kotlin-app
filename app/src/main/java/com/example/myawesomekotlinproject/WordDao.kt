package com.example.myawesomekotlinproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


// WordDao is an interface. DAOs must be either interfaces or
// abstract classes
@Dao
interface WordDao {
    // Self documenting simple query
    @Query("SELECT * FROM word_table ORDER BY word ASC")

    // To observe data changes in the database, we use a Flow return type.
    // A Flow is an async sequence of values. Values are produced one at a time, and
    // values can be generated from async operations (e.g. network requests, db calls).
    // Flows support coroutines, so coroutines can be used to transform Flows (into e.g. LiveData).
    fun getAlphabetizedWords(): Flow<List<Word>>

    // The following is an async insert function to insert
    // one word.

    // @Insert allows us to not add any SQL. @Update and @Delete
    // are also available

    // The selected onConflict strategy ignores a new word
    // if it's exactly the same as an existing word
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    // There is no annotation for deleting multiple entities,
    // so we use the generic query.
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}