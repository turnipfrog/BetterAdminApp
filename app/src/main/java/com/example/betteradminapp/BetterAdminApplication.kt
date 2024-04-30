package com.example.betteradminapp

import android.app.Application
import com.example.betteradminapp.data.AppContainer
import com.example.betteradminapp.data.AppDataContainer

class BetterAdminApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}