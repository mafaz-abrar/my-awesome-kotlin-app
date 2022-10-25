package com.example.myawesomekotlinproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Annotations are self documenting.
// An Entity is a table.
@Entity(tableName = "word_table")
data class Word(
    // If the @ColumnInfo annotation is not used, the column
    // name defaults to the member variable name
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="word") val word: String
    ) {
}