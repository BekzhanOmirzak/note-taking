package com.android.notetaking.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.notetaking.R
import com.android.notetaking.domain.entities.NoteDto

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteAdapter(private val context: Context, private val noteListener: NoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes = listOf<NoteDto>()

    fun updateNotesList(notes: List<NoteDto>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view, noteListener)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

    }

    override fun getItemCount() = 30

    inner class NoteHolder(v: View, noteListener: NoteClickListener) : RecyclerView.ViewHolder(v) {
        val noteTitle = v.findViewById<TextView>(R.id.noteTitle)
        val noteDetail = v.findViewById<TextView>(R.id.noteDetails)
        val noteCheckBox = v.findViewById<CheckBox>(R.id.checkBox)
        val noteCreatedDate = v.findViewById<TextView>(R.id.noteCreateDate)

        init {
            v.setOnClickListener {
                noteListener.onNoteClicked(notes[layoutPosition])
            }
        }

    }

    interface NoteClickListener {
        fun onNoteClicked(note: NoteDto)
    }

}