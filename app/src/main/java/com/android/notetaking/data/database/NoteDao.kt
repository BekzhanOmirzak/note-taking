package com.android.notetaking.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.android.notetaking.domain.entities.NoteDb
import kotlinx.coroutines.flow.Flow

/**
 * Created by bekjan on 30.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(noteDb: NoteDb)

    @Delete
    suspend fun deletedNote(nodeDb: NoteDb)

    @Delete
    suspend fun deletedNotes(nodeDbs: List<NoteDb>)

    @Update
    suspend fun updateNote(nodeDb: NoteDb)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun getNoteById(id: Int): NoteDb


}