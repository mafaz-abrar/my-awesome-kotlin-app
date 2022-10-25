package com.example.myawesomekotlinproject

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// We want to have only a single instance of the
// Database and Repository classes.

// To achieve this, we make them members of the Application
// class; now, they will simply be retrieved rather than constructed
class WordApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using 'by lazy' so these values are constructed when needed
    // rather than at application start
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}