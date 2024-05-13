package com.example.betteradminapp.data.local

import com.example.betteradminapp.data.MessageRepository
import com.example.betteradminapp.data.model.Message
import kotlinx.coroutines.flow.Flow

class OfflineMessageRepository(private val messageDao: MessageDao): MessageRepository {
    override fun getAllMessagesStream(): Flow<List<Message>> = messageDao.getAllMessages()

    override fun getMessagesByIdStream(id: Int): Flow<Message> = messageDao.getMessageById(id)

    override fun getMessagesByReceiverEmailStream(receiverEmail: String): Flow<List<Message>> = messageDao.getMessagesByReveiverEmail(receiverEmail)

    override fun getMessagesBySenderEmailStream(senderEmail: String): Flow<List<Message>> = messageDao.getMessagesBySenderEmail(senderEmail)

    override suspend fun insertMessage(message: Message) = messageDao.insert(message)

    override suspend fun deleteMessage(message: Message) = messageDao.delete(message)

    override suspend fun updateMessage(message: Message) = messageDao.update(message)
}