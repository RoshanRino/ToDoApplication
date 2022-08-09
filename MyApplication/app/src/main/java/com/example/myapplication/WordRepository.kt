package com.example.myapplication


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class WordRepository(private val wordDao: WordDao) {

    val words : LiveData<List<Word>> = wordDao.getWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: String) {
        wordDao.insert(Word(word,false))
    }
    suspend fun deleteAll(){
        wordDao.deleteChecked()
    }

    suspend fun update(word: Word){
        wordDao.update(word)
    }

    suspend fun deleteSwiped(word: Word){
        wordDao.deleteSwiped(word.id)
    }
}