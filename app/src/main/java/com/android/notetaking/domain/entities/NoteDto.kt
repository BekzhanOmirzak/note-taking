package com.android.notetaking.domain.entities

import android.graphics.Bitmap

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteDto(
    val id: Int,
    val name: String,
    val content: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val noteImage: Bitmap
) {


    fun toNoteDb(): NoteDb = NoteDb(
        name = name,
        content = content,
        noteImage = noteImage,
        dateCreated = dateCreated,
        dateUpdated = dateUpdated
    )

}