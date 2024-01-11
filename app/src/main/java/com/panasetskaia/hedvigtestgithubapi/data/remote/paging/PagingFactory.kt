package com.panasetskaia.hedvigtestgithubapi.data.remote.paging

import com.panasetskaia.hedvigtestgithubapi.data.remote.NetworkingImpl
import com.panasetskaia.hedvigtestgithubapi.data.remote.RepoMapper

class PagingFactory(private val client: NetworkingImpl, private val mapper: RepoMapper) {

    fun create() = PagingSource(client, mapper)

}