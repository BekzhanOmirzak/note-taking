package com.android.notetaking.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.notetaking.domain.entities.NoteDb
import com.android.notetaking.domain.entities.NoteDto
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var note: NoteDb
    var counter = 0

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        noteDao = db.noteDao
        note = NoteDto(
            1,
            "I am name",
            "I am content",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            null
        ).toNoteDb()
    }

    @Test
    fun insertNoteAndItExists() = runBlocking {
        noteDao.insertNote(note)
        val noteList = noteDao.getAllNotes()
        assertThat(noteList.contains(note)).isTrue()
    }

    @Test
    fun deleteNoteAfterReturnTrue() = runBlocking {
        noteDao.insertNote(note)
        noteDao.deletedNote(note)
        val noteList = noteDao.getAllNotes()
        assertThat(noteList.isEmpty()).isTrue()
    }

    @Test
    fun updateNoteById() = runBlocking {
        noteDao.insertNote(note)
        val updatedNote = NoteDto(
            note.id,
            "I am updated name",
            "I am update content",
            note.dateCreated,
            System.currentTimeMillis(),
            null
        ).toNoteDb()
        noteDao.updateNote(updatedNote)
        val noteList = noteDao.getAllNotes()
        assertThat(noteList.contains(updatedNote)).isTrue()
    }


    @After
    fun closeDb() {
        db.close()
    }


}