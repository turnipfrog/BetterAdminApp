package com.example.betteradminapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.model.Enrollment
import com.example.betteradminapp.data.model.Event
import com.example.betteradminapp.data.model.Message
import com.example.betteradminapp.data.model.Pupil
import com.example.betteradminapp.data.model.Teacher

@Database(entities = [Pupil::class, Teacher::class, Course::class, Enrollment::class, Message::class, Event::class], version = 8, exportSchema = false)
abstract class BetterAdminDatabase : RoomDatabase() {
    abstract fun pupilDao() : PupilDao
    abstract fun teacherDao(): TeacherDao
    abstract fun courseDao(): CourseDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun messageDao(): MessageDao
    abstract fun eventDao(): EventDao

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