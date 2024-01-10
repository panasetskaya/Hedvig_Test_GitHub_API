package com.panasetskaia.hedvigtestgithubapi.domain

import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity

interface MainRepository {
    suspend fun searchForUsersByQuery(query: String): NetworkResult<List<GitHubUser>>
    suspend fun searchForRepositoriesByUser(user: GitHubUser): NetworkResult<List<RepoEntity>>
    suspend fun getRepoDetails(repo: RepoEntity): NetworkResult<RepoDetails>
}