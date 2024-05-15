package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.MessageRepository
import com.example.betteradminapp.data.TeacherRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Message
import com.example.betteradminapp.data.tools.DateTools
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date


class SendMessageViewModel(
    teacherRepository: TeacherRepository,
    val messageRepository: MessageRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var userEmail = ""
        private set
    var receiverEmail = ""
        private set
    var messageTitle = ""
        private set
    var messageContent = ""
        private set


    val sendMessageUiState: StateFlow<SendMessageUiState> =
        teacherRepository.getAllTeachersStream().map { SendMessageUiState(it.map { teacher -> teacher.email}) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SendMessageUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    init {
        setUserEmail()
    }

    fun setUserEmail() {
        viewModelScope.launch {
            userEmail = userPreferencesRepository.readEmail() ?: ""
        }
    }

    fun setReceiverEmail(email: String) {
        receiverEmail = email
    }

    fun setMessageTitle(title: String) {
        messageTitle = title
    }

    fun setMessageContent(content: String) {
        messageContent = content
    }

    fun validateMessage(): MessageValidity {
        return when {
            !sendMessageUiState.value.teacherEmails.contains(receiverEmail, true) -> MessageValidity.RECEIVER_ISSUE
            messageTitle == "" -> MessageValidity.TITLE_ISSUE
            messageContent == "" -> MessageValidity.CONTENT_ISSUE
            else -> MessageValidity.VALID
        }
    }

    fun List<String>.contains(s: String, ignoreCase: Boolean = false): Boolean {

        return any { it.equals(s, ignoreCase) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage() {
        viewModelScope.launch {
            messageRepository.insertMessage(
                Message(
                    title = messageTitle,
                    content = messageContent,
                    timeSent = DateTools.convertToDateFromLocalDateTime(LocalDateTime.now()) ?: Date(0),
                    senderEmail = userEmail,
                    receiverEmail = receiverEmail,
                    isNew = true
                )
            )
        }
    }
}

data class SendMessageUiState(
    val teacherEmails: List<String> = listOf()
)

enum class MessageValidity {
    VALID, TITLE_ISSUE, CONTENT_ISSUE, RECEIVER_ISSUE
}