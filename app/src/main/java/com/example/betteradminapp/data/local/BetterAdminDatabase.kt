package com.example.betteradminapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.betteradminapp.data.model.Pupil

@Database(entities = [Pupil::class], version = 2, exportSchema = false)
abstract class BetterAdminDatabase : RoomDatabase() {
    abstract fun pupilDao() : PupilDao

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