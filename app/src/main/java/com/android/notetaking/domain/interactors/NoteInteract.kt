package com.android.notetaking.domain.interactors

import com.android.notetaking.domain.entities.NoteDb
import kotlinx.coroutines.flow.Flow

/**
 * Created by bekjan on 30.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */


interface NoteInteract {

    suspend fun insertNote(noteDb: NoteDb)
    suspend fun updateNote(noteDb: NoteDb)
    suspend fun getNoteById(id: Int): NoteDb
    suspend fun deleteNote(noteDb: NoteDb)
    fun getAllFlowNotes(): Flow<List<NoteDb>>
    suspend fun deleteNoteList(notes: List<NoteDb>)


}