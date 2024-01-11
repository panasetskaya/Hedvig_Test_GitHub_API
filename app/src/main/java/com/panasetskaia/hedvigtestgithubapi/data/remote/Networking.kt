package com.panasetskaia.hedvigtestgithubapi.data.remote

interface Networking {
    companion object {

        fun factory(query: String?): NetworkingImpl =
            NetworkingImpl(query)
    }
}