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

    @Query("SELECT * FROM notes ORDER BY date_updated DESC")
    fun getFlowAllNotes(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteDb>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun getNoteById(id: Int): NoteDb

    @Delete
    suspend fun deleteNotes(notes: List<NoteDb>)

}