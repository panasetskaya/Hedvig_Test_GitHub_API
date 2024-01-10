package com.panasetskaia.hedvigtestgithubapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRepoResponse(

    @SerialName("id")
    val id: Long?,

    @SerialName("name")
    val title: String?,

    @SerialName("languages_url")
    val languagesUrl: String?,

    @SerialName("contributors_url")
    val contributorsUrl: String?

)
