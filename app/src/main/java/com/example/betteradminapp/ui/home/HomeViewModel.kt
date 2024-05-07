package com.example.betteradminapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.PupilRepository
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.springframework.security.crypto.bcrypt.BCrypt

class HomeViewModel(pupilRepository: PupilRepository): ViewModel() {

    var passwordAttempt by mutableStateOf("")
        private set

    var emailAttempt by mutableStateOf("")
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    val homeUiState: StateFlow<HomeUiState> =
        pupilRepository.getAllPupilsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateEmailAttempt(input: String) {
        emailAttempt = input
    }

    fun updatePasswordAttempt(input: String) {
        passwordAttempt = input
    }

    fun togglePasswordVisible() {
        passwordVisible = !passwordVisible
    }

    fun validateLogin(email: String, password: String): Boolean {
        val userPassword = homeUiState.value.users.find { email == it.email }?.hashedSaltedPassword

        return if (userPassword == null)
            false
        else
            BCrypt.checkpw(password, userPassword)
    }
}

data class HomeUiState(val users: List<Pupil> = listOf())