package com.example.betteradminapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.model.Pupil
import com.example.betteradminapp.data.model.Teacher

@Database(entities = [Pupil::class, Teacher::class, Course::class], version = 3, exportSchema = false)
abstract class BetterAdminDatabase : RoomDatabase() {
    abstract fun pupilDao() : PupilDao
    abstract fun teacherDao(): TeacherDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var Instance: BetterAdminDatabase? = null

        fun getDatabase(context: Context): BetterAdminDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BetterAdminDatabase::class.java, "better_admin_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}