package com.android.notetaking.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.domain.interactors.NoteInteract
import com.android.notetaking.presentation.tools.VLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class HomeFragmentViewModel @Inject constructor(private val noteInteract: NoteInteract) :
    ViewModel() {

    private val _allNotes = MutableStateFlow<List<NoteDto>?>(null)
    val allNotes: Flow<List<NoteDto>?> = _allNotes
    var job: Job? = null

    init {
        observerAllNotes()
    }

    fun observerAllNotes() {
        job?.cancel()
        job = viewModelScope.launch {
            noteInteract.getAllNotes().collect {
                _allNotes.emit(it.map { it.toNoteDto() }.toList())
            }
        }
    }


}