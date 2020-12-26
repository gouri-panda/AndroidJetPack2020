package com.example.plainolnotes4

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.plainolnotes4.data.AppDatabase
import com.example.plainolnotes4.data.NoteEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    fun deleteSelectNotes(noteEntitys: List<NoteEntity>, onComplete: () -> Unit): Boolean {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteEntitys.forEach {
                    database?.noteDao()?.deleteNOte(it)
                }
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
}