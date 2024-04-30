package com.example.betteradminapp.data

import android.content.Context
import com.example.betteradminapp.data.local.BetterAdminDatabase
import com.example.betteradminapp.data.local.OfflinePupilRepository

interface AppContainer {
    val pupilRepository: PupilRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val pupilRepository: PupilRepository by lazy {
        OfflinePupilRepository(BetterAdminDatabase.getDatabase(context).pupilDao())
    }
}