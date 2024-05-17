package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.CourseRepository
import com.example.betteradminapp.data.MessageRepository
import com.example.betteradminapp.data.PupilRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.model.Pupil
import com.example.betteradminapp.data.tools.DateTools
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(
    val pupilRepository: PupilRepository,
    val courseRepository: CourseRepository,
    val messageRepository: MessageRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _mainUiState = MutableStateFlow(
        MainUiState()
    )

    val mainUiState: StateFlow<MainUiState> = _mainUiState

    val today = DateTools.convertToDateViaInstant(LocalDate.now()) ?: Date()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val userId = userPreferencesRepository.readUserId() ?: -1
            val user = pupilRepository.getPupilByIdStream(userId).firstOrNull()
            val courses = courseRepository.getCoursesByPupilIdAndDateStream(userId, today)
                .firstOrNull()
                ?.distinctBy { course -> course.courseName }

            _mainUiState.value = MainUiState(userId, user, courses)
        }
    }

    fun unreadReceivedMessages(func: (Int) -> Unit) {
        viewModelScope.launch {
            val userEmail = userPreferencesRepository.readEmail() ?: ""
            val unreadMessages = messageRepository
                .getMessagesByReceiverEmailStream(userEmail)
                .first()
                .count { messages -> messages.isNew }
            func(unreadMessages)
        }
    }
}

data class MainUiState(
    val userId: Int? = -1,
    val user: Pupil? = null,
    val courses: List<Course>? = listOf()
)