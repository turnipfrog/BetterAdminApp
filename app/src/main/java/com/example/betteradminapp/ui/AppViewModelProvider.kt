package com.example.betteradminapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.betteradminapp.BetterAdminApplication
import com.example.betteradminapp.ui.home.HomeViewModel
import com.example.betteradminapp.ui.screens.MainViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                betterAdminApplication().container.pupilRepository,
                betterAdminApplication().userPreferencesRepository
            )
        }
        initializer {
            MainViewModel(betterAdminApplication().container.pupilRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [BetterAdminApplication].
 */
fun CreationExtras.betterAdminApplication(): BetterAdminApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BetterAdminApplication)
