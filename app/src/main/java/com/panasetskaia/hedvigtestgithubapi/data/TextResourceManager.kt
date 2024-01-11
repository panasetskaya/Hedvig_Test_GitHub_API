package com.panasetskaia.hedvigtestgithubapi.data

import android.content.Context
import com.panasetskaia.hedvigtestgithubapi.R
import com.panasetskaia.hedvigtestgithubapi.application.GitHubApplication
import javax.inject.Inject

class TextResourceManager @Inject constructor(
    private val application: GitHubApplication
) {
    fun offlineErrorMsg(): String {
        return application.applicationContext.getString(R.string.offline_error)
    }

    fun networkErrorMsg(): String {
        return application.applicationContext.getString(R.string.network_error)
    }

    fun nothingFoundMsg(): String {
        return application.applicationContext.getString(R.string.nothing_found)
    }

    fun somethingWrongMsg(): String {
        return application.applicationContext.getString(R.string.something_wrong)
    }

    fun noRepositoriesMsg(): String {
        return application.applicationContext.getString(R.string.no_repo)
    }

}