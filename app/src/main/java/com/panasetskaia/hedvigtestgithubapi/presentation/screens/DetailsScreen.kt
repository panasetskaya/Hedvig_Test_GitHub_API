package com.panasetskaia.hedvigtestgithubapi.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.panasetskaia.hedvigtestgithubapi.presentation.MainViewModel

@Composable
fun DetailsScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    repoName: String
) {
    Text(text = "Details")
}