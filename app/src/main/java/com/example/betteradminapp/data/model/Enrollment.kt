package com.example.betteradminapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class Enrollment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "pupil_id")
    val pupilId: Int,
    @ColumnInfo(name = "course_id")
    val courseId: Int
)