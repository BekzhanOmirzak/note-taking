package com.android.notetaking.presentation.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.notetaking.R
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.presentation.tools.VLog
import com.android.notetaking.presentation.tools.getFormattedCreateDate

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteAdapter(private val context: Context, private val noteListener: NoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes = listOf<NoteDto>()
    var isSelectMode = false
    val selectedNotes = mutableListOf<NoteDto>()

    fun updateNotesList(notes: List<NoteDto>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindNoteDto(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NoteHolder(val v: View) :
        RecyclerView.ViewHolder(v) {

        val noteTitle = v.findViewById<TextView>(R.id.noteTitle)
        val noteDetail = v.findViewById<TextView>(R.id.noteDetails)
        val noteCheckBox = v.findViewById<CheckBox>(R.id.note_checked)
        val noteCreatedDate = v.findViewById<TextView>(R.id.noteCreateDate)
        val noteCard = v.findViewById<CardView>(R.id.noteCard)

        fun bindNoteDto(noteDto: NoteDto) {
            noteTitle.text = noteDto.title
            noteDetail.text = noteDto.content
            noteCreatedDate.text = getFormattedCreateDate(noteDto.dateUpdated)
            noteCheckBox.isChecked = noteDto.isChecked
            if (isSelectMode)
                noteCheckBox.visibility = View.VISIBLE
            else
                noteCheckBox.visibility = View.GONE

            noteCard.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {
                    isSelectMode = !isSelectMode
                    noteDto.isChecked = true
                    if (!isSelectMode) {
                        notes.forEach { it.isChecked = false }
                        selectedNotes.clear()
                    } else {
                        selectedNotes.add(noteDto)
                    }
                    noteListener.checkedNotes(selectedNotes)
                    notifyDataSetChanged()
                    return true
                }
            })

            noteCard.setOnClickListener {
                if (isSelectMode) {
                    noteDto.isChecked = !noteDto.isChecked
                    noteCheckBox.isChecked = noteDto.isChecked
                    if (noteDto.isChecked)
                        selectedNotes.add(noteDto)
                    else
                        selectedNotes.remove(noteDto)
                    noteListener.checkedNotes(selectedNotes)
                    return@setOnClickListener
                } else
                    noteListener.onNoteClicked(noteDto)
            }
        }
    }

    fun selectAllNotes() {
        isSelectMode = true
        notes.forEach { it.isChecked = true }
        notifyDataSetChanged()
        noteListener.checkedNotes(notes)
    }

    fun unSelectAllNotes() {
        isSelectMode = false
        notes.forEach { it.isChecked = false }
        notifyDataSetChanged()
        selectedNotes.clear()
    }

    interface NoteClickListener {
        fun onNoteClicked(note: NoteDto)
        fun checkedNotes(notes: List<NoteDto>)
    }


}