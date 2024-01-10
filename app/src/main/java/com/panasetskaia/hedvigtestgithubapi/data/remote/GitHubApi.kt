package com.panasetskaia.hedvigtestgithubapi.data.remote

import com.panasetskaia.hedvigtestgithubapi.data.remote.model.ContributorResponse
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.GitHubUserResponse
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.SearchResponse
import com.panasetskaia.hedvigtestgithubapi.data.remote.model.UserRepoResponse
import kotlinx.serialization.json.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("{path_to_contributors}")
    suspend fun getContributors(
        @Path(
            value = "path_to_contributors",
            encoded = true
        ) shortPathToContributors: String
    ): ArrayList<ContributorResponse>

    @GET("{path_to_repositories}")
    suspend fun getRepositories(
        @Path(
            value = "path_to_repositories",
            encoded = true
        ) shortPathToRepositories: String
    ): ArrayList<UserRepoResponse>

    @GET("{path_to_languages}")
    suspend fun getLanguages(
        @Path(
            value = "path_to_languages",
            encoded = true
        ) shortPathToLanguages: String
    ): JsonObject

    @GET("search/users")
    suspend fun searchForUsers(
        @Query(QUERY) query: String = "",
    ): SearchResponse

    companion object {
        private const val QUERY = "q"
    }
}