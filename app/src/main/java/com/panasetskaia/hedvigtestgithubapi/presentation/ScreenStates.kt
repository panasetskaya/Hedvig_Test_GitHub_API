package com.panasetskaia.hedvigtestgithubapi.presentation

import androidx.paging.PagingData
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import kotlinx.coroutines.flow.Flow

sealed class SearchScreenState {

    data object Initial: SearchScreenState()
    data object Loading: SearchScreenState()
    data class UserSearchFinished(val flow: Flow<PagingData<GitHubUser>>): SearchScreenState()
    data class RepoLoadingSuccess(val list: List<RepoEntity>): SearchScreenState()
    data class Failure(val msg: String): SearchScreenState()

}

sealed class DetailsScreenState {
    data object Loading: DetailsScreenState()
    data class Success(val details: RepoDetails): DetailsScreenState()
    data class Failure (val msg: String): DetailsScreenState()

}