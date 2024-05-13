package com.example.betteradminapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.betteradminapp.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: Message)

    @Update
    suspend fun update(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageById(id: Int): Flow<Message>

    @Query("SELECT * FROM messages WHERE sender_email = :senderEmail ORDER BY time_sent DESC")
    fun getMessagesBySenderEmail(senderEmail: String): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE receiver_email = :receiverEmail ORDER BY time_sent DESC")
    fun getMessagesByReveiverEmail(receiverEmail: String): Flow<List<Message>>

    @Query("SELECT * FROM messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<Message>>
}