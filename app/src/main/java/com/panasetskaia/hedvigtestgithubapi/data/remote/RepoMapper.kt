package com.panasetskaia.hedvigtestgithubapi.data.remote

import com.panasetskaia.hedvigtestgithubapi.data.remote.model.GitHubUserResponse
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.UserRepoResponse
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapRepoResponseArrayToEntityList(arrayList: ArrayList<UserRepoResponse>): List<RepoEntity> {
        return arrayList.map { response ->
            mapRepoResponseToRepoEntity(response)
        }
    }

    private fun mapRepoResponseToRepoEntity(response: UserRepoResponse): RepoEntity {
        return RepoEntity(
            response.id,
            response.title
        )
    }

    fun mapUserResponseArrayToEntityList(arrayList: ArrayList<GitHubUserResponse>): List<GitHubUser> {
        return arrayList.map { response ->
            mapUserResponseToEntity(response)
        }
    }

    private fun mapUserResponseToEntity(response: GitHubUserResponse): GitHubUser {
        return GitHubUser(
            response.login,
            response.reposUrl
        )
    }
}