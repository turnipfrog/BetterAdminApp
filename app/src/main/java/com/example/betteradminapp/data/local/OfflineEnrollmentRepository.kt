package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.EnrollmentRepository
import com.example.betteradminapp.data.model.Enrollment
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.Flow

class OfflineEnrollmentRepository(private val enrollmentDao: EnrollmentDao): EnrollmentRepository {
    override fun getAllEnrollmentsStream(): Flow<List<Enrollment>> = enrollmentDao.getAllEnrollments()

    override fun getEnrollmentByIdStream(id: Int): Flow<Enrollment?> = enrollmentDao.getEnrollmentById(id)

    override fun getEnrollmentsByPupilIdStream(pupilId: Int): Flow<List<Enrollment>> = enrollmentDao.getEnrollmentsByPupilId(pupilId)

    override fun getEnrollmentsByCourseIdStream(courseId: Int): Flow<List<Enrollment>> = enrollmentDao.getEnrollmentsByCourseId(courseId)

    override suspend fun insertEnrollment(enrollment: Enrollment) = enrollmentDao.insert(enrollment)

    override suspend fun deleteEnrollment(enrollment: Enrollment) = enrollmentDao.delete(enrollment)

    override suspend fun updateEnrollment(enrollment: Enrollment) = enrollmentDao.update(enrollment)
}