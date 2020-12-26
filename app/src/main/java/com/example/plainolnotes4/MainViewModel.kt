package com.example.plainolnotes4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plainolnotes4.data.AppDatabase
import com.example.plainolnotes4.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val database = AppDatabase.getInstance(application)
    val noteList = database?.noteDao()?.getAllNote()
    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.insertAll(SampleDataProvider.getNotes())
            }
        }
    }

    fun deleteSelectNotes(noteEntities: List<NoteEntity>, onComplete: () -> Unit): Boolean {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteNotes(noteEntities)
                onComplete()
                return@withContext true
            }
        }
        return false
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.insertNote(noteEntity)
            }
        }
    }

    fun deleteAllNotes(): Boolean {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteList?.value?.let {
                    database?.noteDao()?.deleteAllNotesByQuery()
                }
                return@withContext true
            }
        }
        return false
    }
}