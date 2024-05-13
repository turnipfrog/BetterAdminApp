package com.example.betteradminapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.typeconverter.Converters
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
@TypeConverters(Converters::class)
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(course: Course)

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getCourseById(id: Int): Flow<Course>

    @Query("SELECT * FROM courses WHERE teacher_id = :teacherId")
    fun getCoursesByTeacherId(teacherId: Int): Flow<List<Course>>

    @Query("SELECT courses.id, course_name, max_enrolled, start_date, teacher_id, classroom_name " +
            "FROM courses JOIN enrollments ON enrollments.course_id == courses.id " +
            "WHERE pupil_id == :pupilId AND courses.start_date > :date " +
            "ORDER BY start_date ASC")
    fun getCoursesByPupilIdAndDate(pupilId: Int, date: Date): Flow<List<Course>>

    @Query("SELECT * FROM courses ORDER BY id ASC")
    fun getAllCourses(): Flow<List<Course>>
}