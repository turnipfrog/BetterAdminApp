package com.example.betteradminapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.MessageRepository
import com.example.betteradminapp.data.TeacherRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Message
import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageViewModel(
    val teacherRepository: TeacherRepository,
    val messageRepository: MessageRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _messageUiState = MutableStateFlow(
        MessageUiState()
    )

    val messageUiState: StateFlow<MessageUiState> = _messageUiState

    init {
        fetchData()

//        viewModelScope.launch {
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Vigtig info",
//                    content = "Hej alle bla bla bla",
//                    timeSent = Date(2024, 6, 2, 12, 24, 10),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Saksepigen@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Angående Saxofon",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam. " +
//                            "Integer sodales rutrum convallis. Aliquam facilisis gravida leo. " +
//                            "Donec ut magna vel nisl auctor tristique. Ut sit amet ultrices velit, " +
//                            "ut consectetur diam. Etiam iaculis eleifend diam ut feugiat.",
//                    timeSent = Date(2024, 6, 3, 14, 2, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Saksepigen@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Koncert Mandag",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 6, 8, 19, 34, 24),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Saksepigen@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Blå Elefanter",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 6, 3, 12, 0, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Saksepigen@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Giraf Buffet",
//                    content = "Hej alle bla bla bla",
//                    timeSent = Date(2024, 6, 2, 12, 0, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Saksepigen@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Grise i køkkenet",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 6, 5, 12, 2, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Klavermus@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Sorte skyer",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 6, 6, 12, 2, 52),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Klavermus@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Gemmeleg",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 6, 2, 12, 2, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Klavermus@musik.dk",
//                    isNew = true
//                )
//            )
//            messageRepository.insertMessage(
//                message = Message(
//                    title = "Gamle beskeder",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                            "Maecenas ipsum dui, bibendum ut ex vel, dictum facilisis metus. " +
//                            "Aenean euismod tempor ultricies. Praesent imperdiet rhoncus felis non posuere. " +
//                            "Ut eu ante a orci tristique convallis vitae mollis quam.",
//                    timeSent = Date(2024, 5, 6, 12, 2, 0),
//                    receiverEmail = "test@test.dk",
//                    senderEmail = "Klavermus@musik.dk",
//                    isNew = true
//                )
//            )
//        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val userEmail = userPreferencesRepository.readEmail() ?: ""
            val teachers = teacherRepository.getAllTeachersStream().first()
            val messagesSent = messageRepository.getMessagesBySenderEmailStream(userEmail).first()
            val messagesReceived = messageRepository.getMessagesByReceiverEmailStream(userEmail).first()

            _messageUiState.value = MessageUiState(
                messagesSent = messagesSent,
                messagesReceived = messagesReceived,
                teachers = teachers)
        }
    }

    fun setScreenIsReceivedMessages(bool: Boolean) {
        _messageUiState.update {
            it.copy(
                screenIsReceivedMessages = bool
            )
        }
    }

    fun getTeacherNameByEmail(email: String): String {
        val teacher = messageUiState.value.teachers.find { teacher -> teacher.email == email }
        return "${teacher?.firstName} ${teacher?.lastName}"
    }

    suspend fun setMessageSeen(message: Message) {
        messageRepository.updateMessage(message.copy(isNew = false))
    }
}

data class MessageUiState(
    val messagesSent: List<Message> = listOf(),
    val messagesReceived: List<Message> = listOf(),
    val teachers: List<Teacher> = listOf(),
    val screenIsReceivedMessages: Boolean = true
    )