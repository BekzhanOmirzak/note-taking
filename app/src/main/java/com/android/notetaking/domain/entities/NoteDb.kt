package com.android.notetaking.domain.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Entity(tableName = "notes")
data class NoteDb(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "note_image")
    val noteImage: Bitmap?,

    @ColumnInfo(name = "date_created")
    val dateCreated: Long,

    @ColumnInfo(name = "date_updated")
    val dateUpdated: Long

) {

    fun toNoteDto() = NoteDto(
        id = id,
        title = title,
        content = content,
        dateCreated = dateCreated,
        dateUpdated = dateUpdated,
        noteImage = noteImage
    )
}

