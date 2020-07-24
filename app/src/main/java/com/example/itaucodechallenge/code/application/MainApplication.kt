package com.example.itaucodechallenge.code.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

import com.example.itaucodechallenge.code.repository.database.AppDataBase


class RepositoriesApplication : Application() {
    override fun onCreate() {
        setupDB()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
    }

    private fun setupDB() {
        AppDataBase.setupAtApplicationStartup(this)
    }
    
}
