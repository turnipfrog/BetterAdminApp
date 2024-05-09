package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.TeacherRepository
import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.Flow

class OfflineTeacherRepository(private val teacherDao: TeacherDao): TeacherRepository {
    override fun getAllTeachersStream(): Flow<List<Teacher>> = teacherDao.getAllTeachers()

    override fun getTeacherByIdStream(id: Int): Flow<Teacher?> = teacherDao.getTeacherById(id)

    override fun getTeacherByEmailStream(email: String): Flow<Teacher?> = teacherDao.getTeacherByEmail(email)

    override suspend fun insertTeacher(teacher: Teacher) = teacherDao.insert(teacher)

    override suspend fun deleteTeacher(teacher: Teacher) = teacherDao.delete(teacher)

    override suspend fun updateTeacher(teacher: Teacher) = teacherDao.update(teacher)
}