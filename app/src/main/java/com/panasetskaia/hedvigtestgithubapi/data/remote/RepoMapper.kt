package com.panasetskaia.hedvigtestgithubapi.data.remote

import com.panasetskaia.hedvigtestgithubapi.data.remote.model.ContributorDto
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.GitHubUserDto
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.UserRepoDto
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapRepoResponseArrayToEntityList(arrayList: ArrayList<UserRepoDto>): List<RepoEntity> {
        return arrayList.map { dto ->
            mapRepoResponseToRepoEntity(dto)
        }
    }

    private fun mapRepoResponseToRepoEntity(dto: UserRepoDto): RepoEntity {
        return RepoEntity(
            dto.id,
            dto.title,
            dto.languagesUrl,
            dto.contributorsUrl
        )
    }

    fun mapUserResponseArrayToEntityList(arrayList: ArrayList<GitHubUserDto>): List<GitHubUser> {
        return arrayList.map { dto ->
            mapUserResponseToEntity(dto)
        }
    }

    private fun mapUserResponseToEntity(dto: GitHubUserDto): GitHubUser {
        return GitHubUser(
            dto.login,
            dto.reposUrl
        )
    }


    fun mapContributorsDtoArrayToStringList(arrayList: ArrayList<ContributorDto>): List<String> {
        return arrayList.mapNotNull { dto ->
            mapContributorDtoToString(dto)
        }
    }

    private fun mapContributorDtoToString(dto: ContributorDto): String? {
        return dto.login
    }
}