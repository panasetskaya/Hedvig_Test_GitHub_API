package com.panasetskaia.hedvigtestgithubapi.domain

interface SearchRepository {
    suspend fun searchForUsers(query: String): List<RepoEntity>
}