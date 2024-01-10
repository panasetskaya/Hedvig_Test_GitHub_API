package com.panasetskaia.hedvigtestgithubapi.domain.usecases

import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor (private val repo: MainRepository) {

    suspend operator fun invoke(repoId: Long): RepoDetails {
        return repo.getRepoDetails(repoId)
    }

}