package com.panasetskaia.hedvigtestgithubapi.domain

import javax.inject.Inject

class SearchForUsersUseCase @Inject constructor (private val repo: MainRepository) {

    suspend operator fun invoke(query: String): List<RepoEntity> {
        return repo.searchForUsers(query)
    }

}