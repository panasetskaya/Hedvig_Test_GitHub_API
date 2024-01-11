package com.panasetskaia.hedvigtestgithubapi.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.panasetskaia.hedvigtestgithubapi.R
import com.panasetskaia.hedvigtestgithubapi.presentation.MainViewModel
import com.panasetskaia.hedvigtestgithubapi.presentation.SearchScreenState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {

    val searchScreenState by viewModel.searchScreenState

    val context = LocalContext.current

    var query by rememberSaveable {
        mutableStateOf("")
    }

    var isSearchActive by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collectLatest { event ->
                event?.getContentIfNotHandled()?.let { stringResource ->
                    Toast.makeText(
                        context,
                        stringResource,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
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
                    if (it == "") viewModel.backToInitial()
                },
                leadingIcon = {
                    Icon(
                        rememberVectorPainter(image = Icons.Outlined.Search),
                        stringResource(R.string.search_icon)
                    )
                },
                placeholder = { Text(text = stringResource(id = R.string.search_for_user)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                if (searchScreenState is SearchScreenState.UserSearchSuccess) {
                    val items = (searchScreenState as SearchScreenState.UserSearchSuccess).list
                    LazyColumn {
                        items(items) { user ->
                            Text(
                                text = user.login ?: "",
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        top = 4.dp,
                                        end = 8.dp,
                                        bottom = 4.dp
                                    )
                                    .clickable {
                                        isSearchActive = false
                                        viewModel.loadRepos(user)
                                    }
                            )
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
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (searchScreenState is SearchScreenState.RepoLoadingSuccess) {
            val items = (searchScreenState as SearchScreenState.RepoLoadingSuccess).list
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                items(items) { repo ->
                    Text(
                        text = repo.title ?: "",
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 8.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            )
                            .clickable {
                                viewModel.loadDetails(repo)
                            }
                    )
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