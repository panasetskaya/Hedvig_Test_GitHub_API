package com.panasetskaia.hedvigtestgithubapi.domain.usecases

import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import javax.inject.Inject

class SearchForReposUseCase @Inject constructor (private val repo: MainRepository) {

    suspend operator fun invoke(user: GitHubUser): List<RepoEntity> {
        return repo.searchForRepositoriesByUser(user)
    }

}