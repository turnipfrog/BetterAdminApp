package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.PupilRepository
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.Flow

class OfflinePupilRepository(private val pupilDao: PupilDao) : PupilRepository {
    override fun getAllPupilsStream(): Flow<List<Pupil>> = pupilDao.getAllPupils()

    override fun getPupilByIdStream(id: Int): Flow<Pupil?> = pupilDao.getPupilById(id)

    override fun getPupilByEmailStream(email: String): Flow<Pupil?> = pupilDao.getPupilByEmail(email)

    override suspend fun insertPupil(pupil: Pupil) = pupilDao.insert(pupil)

    override suspend fun deletePupil(pupil: Pupil) = pupilDao.delete(pupil)

    override suspend fun updatePupil(pupil: Pupil) = pupilDao.update(pupil)
}