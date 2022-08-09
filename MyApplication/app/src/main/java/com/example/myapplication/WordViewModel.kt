package com.example.myapplication

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val words : LiveData<List<Word>> =repository.words
    fun insert(word: String) = viewModelScope.launch {
        repository.insert(word)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
    }
    fun deleteSwiped(word: Word) = viewModelScope.launch {
        repository.deleteSwiped(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}