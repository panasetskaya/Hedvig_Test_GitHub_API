package com.panasetskaia.hedvigtestgithubapi.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.panasetskaia.hedvigtestgithubapi.R
import com.panasetskaia.hedvigtestgithubapi.data.OfflineError
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import com.panasetskaia.hedvigtestgithubapi.presentation.MainViewModel
import com.panasetskaia.hedvigtestgithubapi.presentation.SearchScreenState
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    onRepoClick: (repo: RepoEntity) -> Unit
) {

    val searchScreenState by viewModel.searchScreenState

    var query by rememberSaveable {
        mutableStateOf("")
    }

    var isSearchActive by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            SearchBar(
                active = isSearchActive,
                query = query,
                onSearch = { viewModel.searchByQuery(it) },
                onActiveChange = { isSearchActive = it },
                onQueryChange = {
                    query = it
                },
                leadingIcon = {
                    if (isSearchActive) {
                        Icon(rememberVectorPainter(image = Icons.Outlined.Clear),
                            stringResource(R.string.clear_search),
                            modifier = Modifier.clickable {
                                query = ""
                                viewModel.backToInitial()
                            })
                    } else {
                        Icon(
                            rememberVectorPainter(image = Icons.Outlined.Search),
                            stringResource(R.string.search_icon)
                        )
                    }

                },
                trailingIcon = {
                    if (isSearchActive) {
                        TrailingSearchButton {
                            viewModel.searchByQuery(query)
                        }
                    }
                },
                placeholder = { Text(text = stringResource(id = R.string.search_for_user)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                if (searchScreenState is SearchScreenState.UserSearchFinished) {
                    val userItems =
                        (searchScreenState as SearchScreenState.UserSearchFinished).flow.collectAsLazyPagingItems()
                    LazyColumn {
                        if (userItems.loadState.prepend is LoadState.Loading) {
                            item(key = "prepend_loading") { SearchLoading() }
                        }
                        if (userItems.loadState.append is LoadState.Loading) {
                            item(key = "append_loading") { SearchLoading() }
                        }
                        if (userItems.loadState.refresh is LoadState.Loading) {
                            item(key = "refresh_loading") { SearchLoading() }
                        }
                        if (userItems.loadState.prepend is LoadState.Error) {
                            item(key = "prepend_error") {
                                SearchError((userItems.loadState.prepend as LoadState.Error).error)
                            }
                        }
                        if (userItems.loadState.append is LoadState.Error) {
                            item(key = "append_error") {
                                SearchError((userItems.loadState.append as LoadState.Error).error)
                            }
                        }
                        if (userItems.loadState.refresh is LoadState.Error) {
                            item(key = "refresh_error") {
                                SearchError((userItems.loadState.refresh as LoadState.Error).error)
                            }
                        }
                        items(userItems.itemCount) { index ->
                            userItems[index]?.let {
                                ItemBox(s = it.login ?: "") {
                                    isSearchActive = false
                                    viewModel.loadRepos(it)
                                }
                            }
                        }
                    }
                }
            }
        }) {
        if (searchScreenState is SearchScreenState.Initial) {
            CenterMessage(
                s = stringResource(id = R.string.look_for_repo),
                paddingValues = it
            )
        }

        if (searchScreenState is SearchScreenState.Loading) {
            CentralProgressIndicator(paddingValues)
        }

        if (searchScreenState is SearchScreenState.RepoLoadingSuccess) {
            val items = (searchScreenState as SearchScreenState.RepoLoadingSuccess).list
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                items(items) { repo ->
                    ItemBox(repo.title ?: "") { onRepoClick(repo) }
                }
            }
        }

        if (searchScreenState is SearchScreenState.Failure) {
            CenterMessage(
                (searchScreenState as SearchScreenState.Failure).msg,
                paddingValues = it
            )
        }
    }
}

@Composable
fun ItemBox(
    s: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = s,
        )
    }
}


@Composable
fun CenterMessage(s: String, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = s,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TrailingSearchButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
    ) {
        Button(
            onClick = { onClick() },
            shape = RectangleShape
        ) {
            Text(text = stringResource(id = R.string.go))
        }
    }

}

@Composable
fun SearchLoading() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SearchError(e: Throwable) {
    val msg = when (e) {
        is HttpException -> {
            Log.d("MYTAG", e.stackTraceToString())
            stringResource(id = R.string.nothing_found)
        }

        is OfflineError -> stringResource(id = R.string.offline_error)
        else -> stringResource(id = R.string.something_wrong)
    }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = msg)
    }
}