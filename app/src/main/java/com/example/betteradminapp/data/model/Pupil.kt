package com.example.betteradminapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.betteradminapp.data.typeconverter.Converters
import java.time.LocalDate

@Entity(tableName = "pupils")
@TypeConverters(Converters::class)
data class Pupil(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val email: String,
    @ColumnInfo(name = "hashed_salted_password")
    val hashedSaltedPassword: String,
    val salt: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "phone_no")
    val phoneNo: String,
    val gender: String,
    @ColumnInfo(name = "enrollment_date")
    val enrollmentDate: LocalDate,
    val note: String?,
    @ColumnInfo(name = "photo_permission")
    val photoPermission: Boolean,
    val school: String,
    val grade: Int,
    val city: String,
    val road: String,
    val postalCode: String,
    @ColumnInfo(name = "guardian_email")
    val guardianEmail: String
    )
