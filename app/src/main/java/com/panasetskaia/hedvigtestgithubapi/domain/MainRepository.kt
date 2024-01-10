package com.panasetskaia.hedvigtestgithubapi.domain

interface MainRepository {
    suspend fun searchForUsers(query: String): List<RepoEntity>
}