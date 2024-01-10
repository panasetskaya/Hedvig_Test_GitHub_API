package com.panasetskaia.hedvigtestgithubapi.data

import android.util.Log
import com.panasetskaia.hedvigtestgithubapi.data.remote.BASE_URL
import com.panasetskaia.hedvigtestgithubapi.data.remote.Networking
import com.panasetskaia.hedvigtestgithubapi.data.remote.RepoMapper
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val networking: Networking,
    private val mapper: RepoMapper
) : MainRepository {

//    private var userRepoCache: Map<Long, UserRepo> = mapOf()
//    private var usernameCache: String? = null

    override suspend fun searchForUsersByQuery(query: String): List<GitHubUser> {
        try {
            val response = networking.githubApi.searchForUsers(query)
            response.items?.let {
                return mapper.mapUserResponseArrayToEntityList(it)
            }
        } catch (httpException: HttpException) {
            Log.d("MYTAG", "we have an error: ${httpException.message()}")
        }
        return listOf()
    }

    override suspend fun searchForRepositoriesByUser(user: GitHubUser): List<RepoEntity> {
        try {
            user.reposUrl?.let {
                val shortPath = getShortPath(it)
                val response = networking.githubApi.getRepositories(shortPath)
                return mapper.mapRepoResponseArrayToEntityList(response)
            }
        } catch (httpException: HttpException) {
            Log.d("MYTAG", "we have an error: ${httpException.message()}")
        }
        return listOf()
    }

    override suspend fun getRepoDetails(repoId: Long): RepoDetails {
        TODO("Not yet implemented")
    }

    //the names of repo languages are the keys in the JsonObject
    private fun decodeKeysFromJson(json: JsonObject): List<String> {
        return json.keys.toList()
    }

    private fun getShortPath(path: String): String {
        return path.replace(BASE_URL, "", true)
    }

}