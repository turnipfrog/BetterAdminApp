package com.example.betteradminapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.CourseRepository
import com.example.betteradminapp.data.PupilRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Course
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    val pupilRepository: PupilRepository,
    val courseRepository: CourseRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _mainUiState = MutableStateFlow(
        MainUiState()
    )

    val mainUiState: StateFlow<MainUiState> = _mainUiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val userId = userPreferencesRepository.readUserId() ?: -1
            val user = pupilRepository.getPupilByIdStream(userId).firstOrNull()
            val courses = courseRepository.getCoursesByPupilIdStream(userId)
                .firstOrNull()
                ?.distinctBy { course -> course.courseName }

            _mainUiState.value = MainUiState(userId, user, courses)
        }
    }
}

data class MainUiState(
    val userId: Int? = -1,
    val user: Pupil? = null,
    val courses: List<Course>? = listOf()
)