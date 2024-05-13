package com.example.betteradminapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(teacher: Teacher)

    @Update
    suspend fun update(teacher: Teacher)

    @Delete
    suspend fun delete(teacher: Teacher)

    @Query("SELECT * FROM teachers WHERE id = :id")
    fun getTeacherById(id: Int): Flow<Teacher>

    @Query("SELECT * FROM teachers WHERE email = :email")
    fun getTeacherByEmail(email: String): Flow<Teacher>

    @Query("SELECT * FROM teachers ORDER BY id ASC")
    fun getAllTeachers(): Flow<List<Teacher>>
}