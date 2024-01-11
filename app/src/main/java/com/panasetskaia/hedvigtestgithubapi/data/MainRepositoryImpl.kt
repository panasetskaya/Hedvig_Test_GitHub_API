package com.panasetskaia.hedvigtestgithubapi.data

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.panasetskaia.hedvigtestgithubapi.application.GitHubApplication
import com.panasetskaia.hedvigtestgithubapi.data.remote.BASE_URL
import com.panasetskaia.hedvigtestgithubapi.data.remote.Networking
import com.panasetskaia.hedvigtestgithubapi.data.remote.RepoMapper
import com.panasetskaia.hedvigtestgithubapi.data.remote.paging.PagingFactory
import com.panasetskaia.hedvigtestgithubapi.data.remote.paging.PagingSourceError
import com.panasetskaia.hedvigtestgithubapi.domain.MainRepository
import com.panasetskaia.hedvigtestgithubapi.domain.NetworkResult
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoDetails
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mapper: RepoMapper,
    private val textResourceManager: TextResourceManager,
    private val application: GitHubApplication
) : MainRepository {

    override suspend fun searchForUsersByQuery(query: String): Flow<PagingData<GitHubUser>> {
        val isNetworkAvailable = isNetworkAvailable()
        if (isNetworkAvailable) {
            val networking = Networking.factory(query)
            val pagingFactory = PagingFactory(networking, mapper)
            return Pager(
                initialKey = INITIAL_PAGE,
                config = PagingConfig(
                    pageSize = PAGE_SIZE
                ),
                pagingSourceFactory = { pagingFactory.create() }
            ).flow
        } else {
            return Pager(
                initialKey = INITIAL_PAGE,
                config = PagingConfig(
                    pageSize = PAGE_SIZE
                ),
                pagingSourceFactory = { PagingSourceError() }
            ).flow
        }
    }

    override suspend fun searchForRepositoriesByUser(user: GitHubUser): NetworkResult<List<RepoEntity>> {
        if (isNetworkAvailable()) {
            try {
                val networking = Networking.factory(null)
                user.reposUrl?.let {
                    val shortPath = getShortPath(it)
                    val response = networking.githubApi.getRepositories(shortPath)
                    return NetworkResult.success(mapper.mapRepoResponseArrayToEntityList(response))
                }
            } catch (exception: Exception) {
                return handleException(exception)
            }
            return NetworkResult.error(textResourceManager.noRepositoriesMsg())
        } else {
            return NetworkResult.error(textResourceManager.offlineErrorMsg())
        }

    }

    override suspend fun getRepoDetails(repo: RepoEntity): NetworkResult<RepoDetails> {
        if (isNetworkAvailable()) {
            return try {
                val networking = Networking.factory(null)
                val contributorsPath = repo.contributorsUrl?.let { getShortPath(it) }
                val languagesPath = repo.languagesUrl?.let { getShortPath(it) }
                val contributorsDtoArray =
                    contributorsPath?.let { networking.githubApi.getContributors(it) }
                        ?: ArrayList()
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
        } else {
            return NetworkResult.error(textResourceManager.offlineErrorMsg())
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(
            application.applicationContext,
            ConnectivityManager::class.java
        )
        val nw = connectivityManager?.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_SIZE = 30
    }

}