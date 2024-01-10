package com.panasetskaia.hedvigtestgithubapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("items")
    val items: ArrayList<GitHubUserDto>?,
)
