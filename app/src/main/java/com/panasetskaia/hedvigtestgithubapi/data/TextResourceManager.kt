package com.panasetskaia.hedvigtestgithubapi.data

import android.content.Context
import com.panasetskaia.hedvigtestgithubapi.R
import javax.inject.Inject

class TextResourceManager @Inject constructor(
    private val appContext: Context
) {
    fun offlineErrorMsg(): String {
        return appContext.applicationContext.getString(R.string.offline_error)
    }

    fun networkErrorMsg(): String {
        return appContext.applicationContext.getString(R.string.network_error)
    }

    fun nothingFoundMsg(): String {
        return appContext.applicationContext.getString(R.string.nothing_found)
    }

    fun somethingWrongMsg(): String {
        return appContext.applicationContext.getString(R.string.something_wrong)
    }

    fun noRepositoriesMsg(): String {
        return appContext.applicationContext.getString(R.string.no_repo)
    }

}