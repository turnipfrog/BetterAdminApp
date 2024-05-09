package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {
    fun getAllTeachersStream(): Flow<List<Teacher>>

    fun getTeacherByIdStream(id: Int): Flow<Teacher?>

    fun getTeacherByEmailStream(email: String): Flow<Teacher?>

    suspend fun insertTeacher(teacher: Teacher)

    suspend fun deleteTeacher(teacher: Teacher)

    suspend fun updateTeacher(teacher: Teacher)
}