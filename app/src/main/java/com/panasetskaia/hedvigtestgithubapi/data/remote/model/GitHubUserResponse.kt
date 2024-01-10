package com.panasetskaia.hedvigtestgithubapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserResponse(

    @SerialName("login")
    val login: String? = null,

    @SerialName("repos_url")
    val reposUrl: String? = null

)