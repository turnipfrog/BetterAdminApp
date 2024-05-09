package com.example.betteradminapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteradminapp.data.model.Enrollment
import kotlinx.coroutines.flow.Flow

@Dao
interface EnrollmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(enrollment: Enrollment)

    @Update
    suspend fun update(enrollment: Enrollment)

    @Delete
    suspend fun delete(enrollment: Enrollment)

    @Query("SELECT * FROM enrollments WHERE id = :id")
    fun getEnrollmentById(id: Int): Flow<Enrollment>

    @Query("SELECT * FROM enrollments WHERE pupil_id = :pupilId")
    fun getEnrollmentsByPupilId(pupilId: Int): Flow<List<Enrollment>>


    @Query("SELECT * FROM enrollments WHERE course_id = :courseId")
    fun getEnrollmentsByCourseId(courseId: Int): Flow<List<Enrollment>>

    @Query("SELECT * from enrollments ORDER BY id ASC")
    fun getAllEnrollments(): Flow<List<Enrollment>>
}