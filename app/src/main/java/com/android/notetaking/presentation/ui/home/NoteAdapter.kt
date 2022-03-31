package com.android.notetaking.presentation.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    var checkedBoxVisible = false
    private val checkedNotes = mutableListOf<NoteDto>()

    fun updateNotesList(notes: List<NoteDto>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view, noteListener)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.apply {
            val note = notes[position]
            noteTitle.setText(note.title.trim())
            noteCreatedDate.setText(getFormattedCreateDate(note.dateCreated))
            noteDetail.setText(note.content.trim())
            noteCheckBox.isEnabled = note.isChecked
        }
        if (checkedBoxVisible)
            holder.noteCheckBox.visibility = View.VISIBLE
        else
            holder.noteCheckBox.visibility = View.GONE

    }

    override fun getItemCount() = notes.size

    inner class NoteHolder(v: View, noteListener: NoteClickListener) :
        RecyclerView.ViewHolder(v) {
        val noteTitle = v.findViewById<TextView>(R.id.noteTitle)
        val noteDetail = v.findViewById<TextView>(R.id.noteDetails)
        val noteCheckBox = v.findViewById<CheckBox>(R.id.note_checked)
        val noteCreatedDate = v.findViewById<TextView>(R.id.noteCreateDate)

        init {
            v.setOnClickListener {
                if (checkedBoxVisible) {
                    notes[layoutPosition].isChecked = !notes[layoutPosition].isChecked
                    noteCheckBox.isChecked = !noteCheckBox.isChecked
                    if (notes[layoutPosition].isChecked) {
                        checkedNotes.add(notes[layoutPosition])
                        checkedBoxListToRemove.add(noteCheckBox)
                    } else
                        checkedNotes.remove(notes[layoutPosition])
                    noteListener.checkedNotes(checkedNotes)
                    notifyDataSetChanged()
                    return@setOnClickListener
                }
                noteListener.onNoteClicked(notes[layoutPosition])
            }
            v.setOnLongClickListener {
                checkedBoxVisible = !checkedBoxVisible
                if (!checkedBoxVisible) {
                    notes.forEach {
                        it.isChecked = false
                    }
                    checkedBoxListToRemove.forEach { it.isEnabled = false }
                    checkedNotes.clear()
                } else {
                    notes[layoutPosition].isChecked = true
                    noteCheckBox.isChecked = true
                    checkedNotes.add(notes[layoutPosition])
                    noteListener.checkedNotes(checkedNotes)
                }
                noteListener.onOnLongClicked()
                notifyDataSetChanged()
                true
            }
        }

    }

    val checkedBoxListToRemove = mutableListOf<CheckBox>()

    interface NoteClickListener {
        fun onNoteClicked(note: NoteDto)
        fun checkedNotes(notes: List<NoteDto>)
        fun onOnLongClicked()
    }

}