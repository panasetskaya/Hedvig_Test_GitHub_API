package com.panasetskaia.hedvigtestgithubapi.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.panasetskaia.hedvigtestgithubapi.data.remote.NetworkingImpl
import com.panasetskaia.hedvigtestgithubapi.data.remote.RepoMapper
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import kotlin.math.ceil


class PagingSource(
    private val client: NetworkingImpl,
    private val mapper: RepoMapper,
) : PagingSource<Int, GitHubUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE
            val response = client.githubApi.searchForUsers(client.query ?: "", nextPage)
            val lastPage = if (response.totalCount>=90) 3 else {
                ceil((response.totalCount.toDouble()/30)).toInt()
            } //GitHub API gives up to 1000 search results, so we can get up to 34 pages,
            // but we have 60 api calls per hour limit, so I limited the pages count to 3
            return LoadResult.Page(
                data = response.items.map {
                    mapper.mapUserResponseToEntity(it)
                },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage == lastPage) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int {
        return FIRST_PAGE
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}