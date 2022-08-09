package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(@ColumnInfo(name = "word") val word: String,
           @ColumnInfo(name="isChecked") var isChecked:Boolean =false,
           @PrimaryKey(autoGenerate = true)@ColumnInfo(name="id") var id:Int = 0)