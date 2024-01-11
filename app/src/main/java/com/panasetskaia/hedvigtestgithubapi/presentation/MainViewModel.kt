package com.panasetskaia.hedvigtestgithubapi.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.hedvigtestgithubapi.R
import com.panasetskaia.hedvigtestgithubapi.application.GitHubApplication
import com.panasetskaia.hedvigtestgithubapi.domain.Status
import com.panasetskaia.hedvigtestgithubapi.domain.models.GitHubUser
import com.panasetskaia.hedvigtestgithubapi.domain.models.RepoEntity
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.GetRepoDetailsUseCase
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForReposUseCase
import com.panasetskaia.hedvigtestgithubapi.domain.usecases.SearchForUsersByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchForRepos: SearchForReposUseCase,
    private val searchForUsers: SearchForUsersByQueryUseCase,
    private val getDetails: GetRepoDetailsUseCase,
    private val application: GitHubApplication
) : ViewModel() {

    private val _searchScreenState: MutableState<SearchScreenState> =
        mutableStateOf(SearchScreenState.Initial)
    val searchScreenState: State<SearchScreenState> = _searchScreenState

    private val _detailsScreenState: MutableState<DetailsScreenState> =
        mutableStateOf(DetailsScreenState.Loading)
    val detailsScreenState: State<DetailsScreenState> = _detailsScreenState

    private val _toastMessage = MutableStateFlow<Event<Int>?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    fun loadDetails(repo: RepoEntity) {
        viewModelScope.launch {
            _detailsScreenState.value = DetailsScreenState.Loading
            val result = getDetails(repo)
            when (result.status) {
                Status.SUCCESS -> {
                    _detailsScreenState.value = result.data?.let { DetailsScreenState.Success(it) }
                        ?: DetailsScreenState.Failure(application.applicationContext.getString(R.string.something_wrong))
                }

                Status.ERROR -> {
                    _detailsScreenState.value = result.msg?.let { DetailsScreenState.Failure(it) }
                        ?: DetailsScreenState.Failure(application.applicationContext.getString(R.string.something_wrong))
                }
            }
        }
    }

    fun searchByQuery(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _searchScreenState.value = SearchScreenState.Loading
                val result = searchForUsers(query)
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            if (it.isEmpty()) {
                                _toastMessage.value =Event(R.string.nothing_found)
                            }
                        }
                        _searchScreenState.value =
                            result.data?.let { SearchScreenState.UserSearchSuccess(it) }
                                ?: SearchScreenState.Failure (application.applicationContext.getString(R.string.something_wrong))
                    }

                    Status.ERROR -> {
                        _searchScreenState.value = result.msg?.let { SearchScreenState.Failure(it) }
                            ?: SearchScreenState.Failure(application.applicationContext.getString(R.string.something_wrong))
                    }
                }
            }
        }
    }

    fun loadRepos(user: GitHubUser) {
        viewModelScope.launch {
            _searchScreenState.value = SearchScreenState.Loading
            val result = searchForRepos(user)
            when (result.status) {
                Status.SUCCESS -> {
                    _searchScreenState.value = result.data?.let { SearchScreenState.RepoLoadingSuccess(it) }
                        ?: SearchScreenState.Failure (application.applicationContext.getString(R.string.no_repo))
                }

                Status.ERROR -> {
                    _searchScreenState.value = result.msg?.let { SearchScreenState.Failure(it) }
                        ?: SearchScreenState.Failure(application.applicationContext.getString(R.string.something_wrong))
                }
            }
        }
    }

    fun backToInitial() {
        _searchScreenState.value = SearchScreenState.Initial
    }
}