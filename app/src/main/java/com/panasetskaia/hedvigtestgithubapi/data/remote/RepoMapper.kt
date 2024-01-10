package com.panasetskaia.hedvigtestgithubapi.data.remote

import com.panasetskaia.hedvigtestgithubapi.data.remote.model.GitHubUserDto
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.UserRepoDto
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapRepoResponseArrayToEntityList(arrayList: ArrayList<UserRepoDto>): List<RepoEntity> {
        return arrayList.map { response ->
            mapRepoResponseToRepoEntity(response)
        }
    }

    private fun mapRepoResponseToRepoEntity(response: UserRepoDto): RepoEntity {
        return RepoEntity(
            response.id,
            response.title,
            response.languagesUrl,
            response.contributorsUrl
        )
    }

    fun mapUserResponseArrayToEntityList(arrayList: ArrayList<GitHubUserDto>): List<GitHubUser> {
        return arrayList.map { response ->
            mapUserResponseToEntity(response)
        }
    }

    private fun mapUserResponseToEntity(response: GitHubUserDto): GitHubUser {
        return GitHubUser(
            response.login,
            response.reposUrl
        )
    }
}