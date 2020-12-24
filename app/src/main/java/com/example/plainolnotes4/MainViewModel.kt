package com.example.plainolnotes4

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plainolnotes4.data.NoteEntity

class MainViewModel : ViewModel() {
    val noteList = MutableLiveData<List<NoteEntity>>()

    init {
        noteList.value = SampleDataProvider.getNotes()
    }

    fun sayHelloWorld() {
        Log.d(TAG, "Hello world")
    }
}