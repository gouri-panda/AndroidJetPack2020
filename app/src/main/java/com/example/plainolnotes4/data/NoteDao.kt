package com.example.plainolnotes4.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(noteEntities: List<NoteEntity>)

    @Delete
    fun deleteNOte(noteEntity: NoteEntity)

    @Delete
    fun deleteNotes(noteEntities: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getAllNote(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getAllByNotes(id: Int): NoteEntity?

    @Query("SELECT COUNT(*) FROM notes")
    fun getRowCounts(): Int

    @Query("SELECT * FROM notes WHERE isSelected = 1")
    fun getSelectedNotes(): LiveData<MutableList<NoteEntity>>?
}