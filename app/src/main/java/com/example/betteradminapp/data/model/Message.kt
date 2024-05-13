package com.example.betteradminapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.betteradminapp.data.typeconverter.Converters
import java.util.Date

@Entity(tableName = "messages")
@TypeConverters(Converters::class)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "time_sent")
    val timeSent: Date,
    @ColumnInfo(name = "sender_email")
    val senderEmail: String,
    @ColumnInfo(name = "receiver_email")
    val receiverEmail: String,
    @ColumnInfo(name = "is_new")
    val isNew: Boolean
)