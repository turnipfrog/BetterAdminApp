package com.example.betteradminapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.MessageRepository
import com.example.betteradminapp.data.TeacherRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Teacher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SendMessageViewModel(
    teacherRepository: TeacherRepository,
    val messageRepository: MessageRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var userEmail = ""

    val sendMessageUiState: StateFlow<SendMessageUiState> =
        teacherRepository.getAllTeachersStream().map { SendMessageUiState(it) }
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
}

data class SendMessageUiState(
    val teachers: List<Teacher> = listOf()
)