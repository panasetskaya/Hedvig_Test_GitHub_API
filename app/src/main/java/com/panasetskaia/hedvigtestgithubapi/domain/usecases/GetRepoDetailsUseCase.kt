package com.panasetskaia.hedvigtestgithubapi.domain.usecases

import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.NetworkResult
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor (private val repo: MainRepository) {

    suspend operator fun invoke(repoEntity: RepoEntity): NetworkResult<RepoDetails> {
        return repo.getRepoDetails(repoEntity)
    }

}