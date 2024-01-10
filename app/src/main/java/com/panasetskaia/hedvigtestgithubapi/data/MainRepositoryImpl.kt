package com.panasetskaia.hedvigtestgithubapi.data

import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.RepoEntity
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(): MainRepository {
    override suspend fun searchForUsers(query: String): List<RepoEntity> {
        TODO("Not yet implemented")
    }
}