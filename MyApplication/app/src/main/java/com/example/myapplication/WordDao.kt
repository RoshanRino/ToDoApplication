package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WordDao {

    @Query("SELECT * FROM word_table")
    fun getWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("DELETE FROM word_table WHERE isChecked = :chk" )
    suspend fun deleteChecked(chk :Boolean =true)

    @Query("DELETE FROM word_table WHERE id = :wordDelete")
    suspend fun deleteSwiped(wordDelete: Int)

    @Update
    suspend fun update(word: Word)
}