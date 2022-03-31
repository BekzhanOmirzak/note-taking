package com.android.notetaking.domain.entities

import android.graphics.Bitmap

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
data class NoteDto(
    val id: Int,
    var title: String,
    var content: String,
    var dateCreated: Long,
    var dateUpdated: Long,
    var noteImage: Bitmap?
) {
    var isChecked: Boolean = false

    fun toNoteDb(): NoteDb = NoteDb(
        id = id,
        title = title,
        content = content,
        noteImage = noteImage,
        dateCreated = dateCreated,
        dateUpdated = dateUpdated
    )

}