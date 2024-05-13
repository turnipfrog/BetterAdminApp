package com.example.betteradminapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.betteradminapp.data.typeconverter.Converters
import java.util.Date

@Entity(tableName = "courses")
@TypeConverters(Converters::class)
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "course_name")
    val courseName: String,
    @ColumnInfo(name = "max_enrolled")
    val maxEnrolled: Int,
    @ColumnInfo(name = "start_date")
    val startDate: Date,
    @ColumnInfo(name = "teacher_id")
    val teacherId: Int,
    @ColumnInfo(name = "classroom_name")
    val classroomName: String
    )