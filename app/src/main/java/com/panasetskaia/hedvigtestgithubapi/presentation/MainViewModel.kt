package com.panasetskaia.hedvigtestgithubapi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.hedvigtestgithubapi.domain.NetworkResult
import com.panasetskaia.hedvigtestgithubapi.domain.Status
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.GetRepoDetailsUseCase
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForReposUseCase
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForUsersByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchForRepos: SearchForReposUseCase,
    private val searchForUsers: SearchForUsersByQueryUseCase,
    private val getDetails: GetRepoDetailsUseCase
): ViewModel() {

    fun test() {
        viewModelScope.launch {
            val response = searchForUsers("panaset")
            Log.d("MYTAG", "$response")
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    if (it.isNotEmpty()) {
                        val repoForFirstUser = searchForRepos(it[0])
                        Log.d("MYTAG", "$repoForFirstUser")
                        if (repoForFirstUser.status == Status.SUCCESS) {
                            repoForFirstUser.data?.let { repos ->
                                if (repos.isNotEmpty()) {
                                    val details = getDetails(repos[0])
                                    Log.d("MYTAG", "$details")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}