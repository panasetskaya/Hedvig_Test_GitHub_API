package com.panasetskaia.hedvigtestgithubapi.domain

data class RepoEntity(
    val title: String,
    val languages: List<String>,
    val contributors: List<String>
)
