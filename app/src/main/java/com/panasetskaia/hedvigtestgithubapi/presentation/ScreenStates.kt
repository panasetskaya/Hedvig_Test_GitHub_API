package com.panasetskaia.hedvigtestgithubapi.presentation

import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity

sealed class SearchScreenState {

    data object Initial: SearchScreenState()
    data object Loading: SearchScreenState()
    data class UserSearchSuccess(val list: List<GitHubUser>): SearchScreenState()
    data class RepoLoadingSuccess(val list: List<RepoEntity>): SearchScreenState()
    data class Failure(val msg: String): SearchScreenState()

}

sealed class DetailsScreenState {
    data object Loading: DetailsScreenState()
    data class Success(val details: RepoDetails): DetailsScreenState()
    data class Failure (val msg: String): DetailsScreenState()

}