package com.example.betteradminapp.data

import com.example.betteradminapp.data.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getAllMessagesStream(): Flow<List<Message>>

    fun getMessagesByIdStream(id: Int): Flow<Message>

    fun getMessagesByReceiverEmailStream(receiverEmail: String): Flow<List<Message>>

    fun getMessagesBySenderEmailStream(senderEmail: String): Flow<List<Message>>

    suspend fun insertMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun updateMessage(message: Message)
}

//@Insert(onConflict = OnConflictStrategy.IGNORE)
//suspend fun insert(message: Message)
//
//@Update
//suspend fun update(message: Message)
//
//@Delete
//suspend fun delete(message: Message)
//
//@Query("SELECT * FROM messages WHERE id = :id")
//fun getMessageById(id: Int): Flow<Message>
//
//@Query("SELECT * FROM messages WHERE sender_email = :senderEmail ORDER BY time_sent DESC")
//fun getMessageBySenderEmail(senderEmail: String): Flow<List<Message>>
//
//@Query("SELECT * FROM messages WHERE receiver_email = :receiverEmail ORDER BY time_sent DESC")
//fun getMessageByReveiverEmail(receiverEmail: String): Flow<List<Message>>
//
//@Query("SELECT * FROM pupils ORDER BY id ASC")
//fun getAllMessages(): Flow<List<Message>>