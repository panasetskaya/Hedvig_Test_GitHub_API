package com.panasetskaia.hedvigtestgithubapi.domain

import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity

interface MainRepository {
    suspend fun searchForUsersByQuery(query: String): List<GitHubUser>
    suspend fun searchForRepositoriesByUser(user: GitHubUser): List<RepoEntity>
    suspend fun getRepoDetails(repoId: Long): RepoDetails
}