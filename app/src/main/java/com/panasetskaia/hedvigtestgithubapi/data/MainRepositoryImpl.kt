package com.panasetskaia.hedvigtestgithubapi.data

import com.panasetskaia.hedvigtestgithubapi.data.remote.BASE_URL
import com.panasetskaia.hedvigtestgithubapi.data.remote.Networking
import com.panasetskaia.hedvigtestgithubapi.data.remote.RepoMapper
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.NetworkResult
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val networking: Networking,
    private val mapper: RepoMapper,
    private val textResourceManager: TextResourceManager
) : MainRepository {

//    private var userRepoCache: Map<Long, UserRepo> = mapOf()
//    private var usernameCache: String? = null

    override suspend fun searchForUsersByQuery(query: String): NetworkResult<List<GitHubUser>> {
        try {
            val response = networking.githubApi.searchForUsers(query)
            response.items?.let {
                return NetworkResult.success(mapper.mapUserResponseArrayToEntityList(it))
            }
        } catch (exception: Exception) {
            return handleException(exception)
        }
        return NetworkResult.error(textResourceManager.nothingFoundMsg())
    }

    override suspend fun searchForRepositoriesByUser(user: GitHubUser): NetworkResult<List<RepoEntity>> {
        try {
            user.reposUrl?.let {
                val shortPath = getShortPath(it)
                val response = networking.githubApi.getRepositories(shortPath)
                return NetworkResult.success(mapper.mapRepoResponseArrayToEntityList(response))
            }
        } catch (exception: Exception) {
            return handleException(exception)
        }
        return NetworkResult.error(textResourceManager.noRepositoriesMsg())
    }

    override suspend fun getRepoDetails(repo: RepoEntity): NetworkResult<RepoDetails> {
        return try {
            val contributorsPath = repo.contributorsUrl?.let { getShortPath(it) }
            val languagesPath = repo.languagesUrl?.let { getShortPath(it) }
            val contributorsDtoArray =
                contributorsPath?.let { networking.githubApi.getContributors(it) } ?: ArrayList()
            val languagesJson = languagesPath?.let { networking.githubApi.getLanguages(it) }
            val contributors = mapper.mapContributorsDtoArrayToStringList(contributorsDtoArray)
            val languages = languagesJson?.let { decodeKeysFromJson(it) } ?: listOf()
            val repoDetails = RepoDetails(
                languages, contributors
            )
            NetworkResult.success(repoDetails)
        } catch (exception: Exception) {
            handleException(exception)
        }
    }

    //the names of repo languages are the keys in the JsonObject
    private fun decodeKeysFromJson(json: JsonObject): List<String> {
        return json.keys.toList()
    }

    private fun getShortPath(path: String): String {
        return path.replace(BASE_URL, "", true)
    }

    private fun <T> handleException(exception: Exception): NetworkResult<T> {
        return when (exception) {
            is HttpException -> NetworkResult.error(textResourceManager.networkErrorMsg())
            is UnknownHostException -> NetworkResult.error(textResourceManager.offlineErrorMsg())
            else -> NetworkResult.error(textResourceManager.somethingWrongMsg())
        }
    }
}