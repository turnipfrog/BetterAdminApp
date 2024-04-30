package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.Flow

interface PupilRepository {

    fun getAllPupilsStream(): Flow<List<Pupil>>

    fun getPupilByIdStream(id: Int): Flow<Pupil?>

    fun getPupilByEmailStream(email: String): Flow<Pupil?>

    suspend fun insertPupil(pupil: Pupil)

    /**
     * Delete item from the data source
     */
    suspend fun deletePupil(pupil: Pupil)

    /**
     * Update item in the data source
     */
    suspend fun updatePupil(pupil: Pupil)
}