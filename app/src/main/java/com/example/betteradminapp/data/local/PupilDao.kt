package com.example.betteradminapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.Flow

@Dao
interface PupilDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pupil: Pupil)

    @Update
    suspend fun update(pupil: Pupil)

    @Delete
    suspend fun delete(pupil: Pupil)

    @Query("SELECT * FROM pupils WHERE id = :id")
    fun getPupilById(id: Int): Flow<Pupil>

    @Query("SELECT * FROM pupils WHERE email = :email")
    fun getPupilByEmail(email: String): Flow<Pupil>

    @Query("SELECT * from pupils ORDER BY id ASC")
    fun getAllPupils(): Flow<List<Pupil>>
}