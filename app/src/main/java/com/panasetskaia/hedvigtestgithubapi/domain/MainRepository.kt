package com.panasetskaia.hedvigtestgithubapi.domain

import androidx.paging.PagingData
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun searchForUsersByQuery(query: String): Flow<PagingData<GitHubUser>>
    suspend fun searchForRepositoriesByUser(user: GitHubUser): NetworkResult<List<RepoEntity>>
    suspend fun getRepoDetails(repo: RepoEntity): NetworkResult<RepoDetails>
}