package com.panasetskaia.hedvigtestgithubapi.application

import android.app.Application
import com.panasetskaia.hedvigtestgithubapi.data.remote.Networking
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitHubApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Networking.init(this)
    }
}