package com.android.notetaking.data.interactors

import com.android.notetaking.data.database.NoteDao
import com.android.notetaking.domain.entities.NoteDb
import com.android.notetaking.domain.interactors.NoteInteract
import javax.inject.Inject

/**
 * Created by bekjan on 30.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteInteractImpl @Inject constructor(private val noteDao: NoteDao) : NoteInteract {


    override suspend fun insertNote(noteDb: NoteDb) = noteDao.insertNote(noteDb)

    override suspend fun updateNote(noteDb: NoteDb) = noteDao.updateNote(noteDb)

    override suspend fun getNoteById(id: Int) = noteDao.getNoteById(id)

    override suspend fun deleteNote(noteDb: NoteDb) = noteDao.deletedNote(noteDb)

    override fun getAllFlowNotes() = noteDao.getFlowAllNotes()

    override suspend fun deleteNoteList(notes: List<NoteDb>) = noteDao.deleteNotes(notes)



}