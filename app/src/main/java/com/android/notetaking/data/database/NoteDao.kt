package com.android.notetaking.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.android.notetaking.domain.entities.NoteDb

/**
 * Created by bekjan on 30.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Dao
interface NoteDao {


    @Insert
    suspend fun saveNote(noteDb: NoteDb)


}