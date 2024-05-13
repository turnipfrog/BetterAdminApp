package com.example.betteradminapp.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.betteradminapp.BetterAdminApplication
import com.example.betteradminapp.ui.home.HomeViewModel
import com.example.betteradminapp.ui.screens.CourseViewModel
import com.example.betteradminapp.ui.screens.MainViewModel
import com.example.betteradminapp.ui.screens.MessageViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                pupilRepository = betterAdminApplication().container.pupilRepository,
                userPreferencesRepository = betterAdminApplication().userPreferencesRepository
            )
        }
        initializer {
            MainViewModel(
                pupilRepository = betterAdminApplication().container.pupilRepository,
                courseRepository = betterAdminApplication().container.courseRepository,
                userPreferencesRepository = betterAdminApplication().userPreferencesRepository
            )
        }
        initializer {
            CourseViewModel(
                enrollmentRepository = betterAdminApplication().container.enrollmentRepository,
                courseRepository = betterAdminApplication().container.courseRepository,
                teacherRepository = betterAdminApplication().container.teacherRepository,
                userPreferencesRepository = betterAdminApplication().userPreferencesRepository
            )
        }
        initializer {
            MessageViewModel(
                teacherRepository = betterAdminApplication().container.teacherRepository,
                messageRepository = betterAdminApplication().container.messageRepository,
                userPreferencesRepository = betterAdminApplication().userPreferencesRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [BetterAdminApplication].
 */
fun CreationExtras.betterAdminApplication(): BetterAdminApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BetterAdminApplication)
