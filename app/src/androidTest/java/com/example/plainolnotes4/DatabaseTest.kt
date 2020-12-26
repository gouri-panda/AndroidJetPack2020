package com.example.plainolnotes4

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plainolnotes4.data.AppDatabase
import com.example.plainolnotes4.data.NoteDao
import com.example.plainolnotes4.data.NoteEntity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    lateinit var database: AppDatabase
    lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()!!
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getRowCount() {
        noteDao.insertAll(SampleDataProvider.getNotes())
        val rowCount = database.noteDao()?.getRowCounts()
        assertEquals(3, rowCount)
    }

    @Test
    fun createANote() {
        noteDao.insertNote(NoteEntity(date = Date(), text = "a note"))
        val note = noteDao.getAllByNotes(1)
        val text = note?.text
        assertEquals(text, "a note")
    }
}