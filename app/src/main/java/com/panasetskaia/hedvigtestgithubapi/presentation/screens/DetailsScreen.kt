package com.panasetskaia.hedvigtestgithubapi.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.panasetskaia.hedvigtestgithubapi.R
import com.panasetskaia.hedvigtestgithubapi.presentation.DetailsScreenState
import com.panasetskaia.hedvigtestgithubapi.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    repoName: String,
    goBack: () -> Unit
) {
    val screenState by viewModel.detailsScreenState

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopAppBar(
                title = { Text(text = repoName) },
                navigationIcon = {
                    Icon(
                        rememberVectorPainter(image = Icons.Outlined.ArrowBack),
                        stringResource(R.string.go_back),
                        modifier = Modifier
                            .clickable {
                                goBack()
                            }
                            .padding(8.dp)
                    )
                }
            )
        }
    ) {

        when (screenState) {
            is DetailsScreenState.Failure -> CenterMessage(
                s = (screenState as DetailsScreenState.Failure).msg,
                paddingValues = it
            )

            is DetailsScreenState.Loading -> {
                CentralProgressIndicator(paddingValues)
            }

            is DetailsScreenState.Success -> {
                val languages =
                    (screenState as DetailsScreenState.Success).details.languages.joinToString(", ")
                val contributors =
                    (screenState as DetailsScreenState.Success).details.contributors.joinToString(", ")
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {

                    item {
                        Text(
                            text = stringResource(id = R.string.languages),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        Text(
                            text = languages,
                            modifier = Modifier.padding(16.dp, 0.dp, 8.dp, 16.dp)
                        )
                    }
                    item {
                        Text(
                            text = stringResource(id = R.string.contributors),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        Text(
                            text = contributors,
                            modifier = Modifier.padding(16.dp, 0.dp, 8.dp, 16.dp)
                        )
                    }
                }
            }
        }
    }
}