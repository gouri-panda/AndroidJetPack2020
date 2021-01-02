package com.example.plainolnotes4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.plainolnotes4.data.AppDatabase
import com.example.plainolnotes4.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    val database = AppDatabase.getInstance(application)
    val currentNote = MutableLiveData<NoteEntity>()
    fun getNoteById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val note = if (id != NEW_NOTE_ID) {
                    database?.noteDao()?.getAllByNotes(id)
                } else {
                    NoteEntity()
                }
                currentNote.postValue(note)
            }
        }
    }

    fun saveNote() {
        currentNote.value?.let {
            if (it.text.isNotBlank() || it.id != NEW_NOTE_ID) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        database?.noteDao()?.insertNote(it)
                    }
                }

            } else {
                //If user lives it blanks then we'll delete it
                if (it.text.isEmpty()) {
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            database?.noteDao()?.deleteNOte(it)
                        }
                    }
                }
                return
            }
        }
    }

}