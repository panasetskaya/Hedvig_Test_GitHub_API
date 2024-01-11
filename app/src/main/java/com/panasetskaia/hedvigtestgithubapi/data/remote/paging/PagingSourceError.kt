package com.panasetskaia.hedvigtestgithubapi.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.panasetskaia.hedvigtestgithubapi.data.OfflineError
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser

class PagingSourceError : PagingSource<Int, GitHubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        return try {
            throw OfflineError()
        } catch (e: OfflineError) {
            Log.d("MYTAG", "PagingSourceError: error caught")
            LoadResult.Error(e)
        }
    }
}