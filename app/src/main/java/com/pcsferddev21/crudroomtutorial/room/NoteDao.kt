package com.pcsferddev21.crudroomtutorial.room

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note")
    suspend fun getNote(): List<Note>

    @Query("SELECT * FROM Note WHERE id=:note_id")
    suspend fun getNote(note_id: Int): List<Note>
}