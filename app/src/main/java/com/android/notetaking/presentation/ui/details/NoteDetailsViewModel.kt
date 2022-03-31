package com.android.notetaking.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.domain.interactors.NoteInteract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */


class NoteDetailsViewModel @Inject constructor(private val noteInteract: NoteInteract) :
    ViewModel() {

    private val _noteById = MutableStateFlow<NoteDto?>(null)
    val noteById: StateFlow<NoteDto?> = _noteById

    fun insertNote(noteDto: NoteDto) {
        viewModelScope.launch {
            noteInteract.insertNote(noteDto.toNoteDb())
        }
    }

    fun updateNote(noteDto: NoteDto) {
        viewModelScope.launch {
            noteInteract.updateNote(noteDto.toNoteDb())
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            val note = noteInteract.getNoteById(id)
            _noteById.emit(note.toNoteDto())
        }
    }

    fun deleteNote(noteDto: NoteDto) {
        viewModelScope.launch {
            noteInteract.deleteNote(noteDto.toNoteDb())
        }
    }


}