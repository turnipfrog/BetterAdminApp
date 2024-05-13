package com.example.betteradminapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteradminapp.data.PupilRepository
import com.example.betteradminapp.data.UserPreferencesRepository
import com.example.betteradminapp.data.model.Pupil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.springframework.security.crypto.bcrypt.BCrypt

class HomeViewModel(
    pupilRepository: PupilRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

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

    init {
        setCredentialsFromDataStore()

//        val salt = BCrypt.gensalt()
//        viewModelScope.launch {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                pupilRepository.insertPupil(
//                    Pupil(
//                        email = "test@test.dk",
//                        hashedSaltedPassword = BCrypt.hashpw("test", salt),
//                        salt = salt,
//                        firstName = "Hund",
//                        lastName = "Hundesen",
//                        phoneNo = "12345678",
//                        gender = "m",
//                        enrollmentDate = LocalDate.of(2024, 5, 8),
//                        note = null,
//                        photoPermission = false,
//                        school = "Doggy school",
//                        grade = 2,
//                        city = "Dogtown",
//                        road = "Hundevej 2",
//                        postalCode = "1234",
//                        guardianEmail = null
//                    )
//                )
//            }
//        }
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

    fun clearLoginAttempt() {
        emailAttempt = ""
        passwordAttempt = ""
    }

    fun validateLogin(email: String, password: String): Boolean {
        val userPassword = homeUiState.value.users.find { email == it.email }?.hashedSaltedPassword

        return if (userPassword == null)
            false
        else
            BCrypt.checkpw(password, userPassword)
    }

    fun storeCredentialsLocally(email: String, password: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveEmail(email)
            userPreferencesRepository.savePassword(password)
            userPreferencesRepository.saveUserId(homeUiState.value.users.find { email == it.email }?.id ?: -1)
        }
    }

    private fun setCredentialsFromDataStore() {
        viewModelScope.launch {
            emailAttempt = userPreferencesRepository.readEmail() ?: ""
            passwordAttempt = userPreferencesRepository.readPassword() ?: ""
        }
    }
}

data class HomeUiState(val users: List<Pupil> = listOf())