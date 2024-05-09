package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Enrollment
import kotlinx.coroutines.flow.Flow

interface EnrollmentRepository {
    fun getAllEnrollmentsStream(): Flow<List<Enrollment>>

    fun getEnrollmentByIdStream(id: Int): Flow<Enrollment?>

    fun getEnrollmentsByPupilIdStream(pupilId: Int): Flow<List<Enrollment>>

    fun getEnrollmentsByCourseIdStream(courseId: Int): Flow<List<Enrollment>>

    suspend fun insertEnrollment(enrollment: Enrollment)

    suspend fun deleteEnrollment(enrollment: Enrollment)

    suspend fun updateEnrollment(enrollment: Enrollment)
}