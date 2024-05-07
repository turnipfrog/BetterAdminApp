package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.Flow

interface PupilRepository {

    fun getAllPupilsStream(): Flow<List<Pupil>>

    fun getPupilByIdStream(id: Int): Flow<Pupil?>

    fun getPupilByEmailStream(email: String): Flow<Pupil?>

    suspend fun insertPupil(pupil: Pupil)

    suspend fun deletePupil(pupil: Pupil)

    suspend fun updatePupil(pupil: Pupil)
}