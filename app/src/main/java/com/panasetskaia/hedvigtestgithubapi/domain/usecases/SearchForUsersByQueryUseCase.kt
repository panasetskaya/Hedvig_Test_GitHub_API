package com.panasetskaia.hedvigtestgithubapi.domain.usecases

import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import javax.inject.Inject

class SearchForUsersByQueryUseCase @Inject constructor(private val repo: MainRepository) {
    suspend operator fun invoke(query: String): List<GitHubUser> {
        return repo.searchForUsersByQuery(query)
    }

}