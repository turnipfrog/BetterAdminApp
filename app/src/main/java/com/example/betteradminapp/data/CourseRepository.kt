package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Course
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CourseRepository {
    fun getAllCoursesStream(): Flow<List<Course>>

    fun getCourseByIdStream(id: Int): Flow<Course?>

    fun getCoursesByTeacherIdStream(teacherId: Int): Flow<List<Course>>

    fun getCoursesByPupilIdAndDateStream(pupilId: Int, date: Date): Flow<List<Course>>

    suspend fun insertCourse(course: Course)

    suspend fun deleteCourse(course: Course)

    suspend fun updateCourse(course: Course)
}