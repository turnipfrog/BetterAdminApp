package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.CourseRepository
import com.example.betteradminapp.data.model.Course
import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineCourseRepository(private val courseDao: CourseDao): CourseRepository {
    override fun getAllCoursesStream(): Flow<List<Course>> = courseDao.getAllCourses()

    override fun getCourseByIdStream(id: Int): Flow<Course?> = courseDao.getCourseById(id)

    override fun getCoursesByTeacherIdStream(teacherId: Int): Flow<List<Course>> = courseDao.getCoursesByTeacherId(teacherId)

    override fun getCoursesByPupilIdAndDateStream(pupilId: Int, date: Date): Flow<List<Course>> = courseDao.getCoursesByPupilIdAndDate(pupilId, date)

    override suspend fun insertCourse(course: Course) = courseDao.insert(course)

    override suspend fun deleteCourse(course: Course) = courseDao.delete(course)

    override suspend fun updateCourse(course: Course) = courseDao.update(course)
}