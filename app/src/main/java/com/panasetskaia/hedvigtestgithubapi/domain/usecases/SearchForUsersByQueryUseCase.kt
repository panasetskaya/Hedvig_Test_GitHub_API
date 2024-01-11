package com.panasetskaia.hedvigtestgithubapi.domain.usecases

import androidx.paging.PagingData
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.NetworkResult
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchForUsersByQueryUseCase @Inject constructor(private val repo: MainRepository) {
    suspend operator fun invoke(query: String): Flow<PagingData<GitHubUser>> {
        return repo.searchForUsersByQuery(query)
    }

}