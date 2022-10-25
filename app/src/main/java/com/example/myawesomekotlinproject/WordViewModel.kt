package com.example.myawesomekotlinproject

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData allows us to put an observer on the data, rather
    // than polling for data changes. The Repository is also completely
    // separated from the UI through the ViewModel
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // Launch a new scope to insert data in a non blocking way.
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

// Set up a simple factory to create the ViewModel
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}