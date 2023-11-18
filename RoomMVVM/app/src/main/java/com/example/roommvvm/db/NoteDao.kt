package com.example.roommvvm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    //live data get realtime updates of data
    @Query("SELECT * FROM notes_table")
    fun getAllNotes() : LiveData<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}