package com.panasetskaia.hedvigtestgithubapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContributorDto(

    @SerialName("login")
    val login: String? = null
)
