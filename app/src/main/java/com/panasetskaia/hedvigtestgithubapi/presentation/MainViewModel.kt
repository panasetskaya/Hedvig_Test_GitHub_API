package com.panasetskaia.hedvigtestgithubapi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForReposUseCase
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForUsersByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchForRepos: SearchForReposUseCase,
    private val searchForUsers: SearchForUsersByQueryUseCase
): ViewModel() {

    fun test() {
        viewModelScope.launch {
            val response = searchForUsers("panaset")
            Log.d("MYTAG", "$response")
        }
    }
}