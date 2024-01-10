package com.panasetskaia.hedvigtestgithubapi.data

import com.panasetskaia.hedvigtestgithubapi.data.remote.BASE_URL
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.UserRepo
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.RepoEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class MainRepositoryImpl: MainRepository {

    private var userRepoCache: Map<Long, UserRepo> = mapOf()

    override suspend fun searchForUsers(query: String): List<RepoEntity> {
        TODO("Not yet implemented")

    }

    override suspend fun getRepoDetails(repoId: Long): RepoDetails {
        TODO("Not yet implemented")
    }

    //the names of repo languages are the keys in the JsonObject
    private fun decodeKeysFromJson(json: JsonObject): List<String> {
        return json.keys.toList()
    }

    private fun getShortPath(path: String): String {
        return path.replace(BASE_URL,"",true)
    }

}