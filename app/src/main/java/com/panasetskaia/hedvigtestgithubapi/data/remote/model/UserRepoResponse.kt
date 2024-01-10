package com.panasetskaia.hedvigtestgithubapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRepoResponse(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val title: String? = null,

    @SerialName("languages_url")
    val languagesUrl: String? = null,

    @SerialName("contributors_url")
    val contributorsUrl: String? = null

)
