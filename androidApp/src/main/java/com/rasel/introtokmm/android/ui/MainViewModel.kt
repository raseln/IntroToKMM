package com.rasel.introtokmm.android.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasel.introtokmm.android.data.MainUiState
import com.rasel.introtokmm.shared.SpaceXSDK
import kotlinx.coroutines.launch

class MainViewModel(private val sdk: SpaceXSDK): ViewModel() {

    var uiState by mutableStateOf(MainUiState(isLoading = true))
        private set

    fun getLaunches(
        forceRefresh: Boolean = false
    ) {
        uiState = uiState.copy(isLoading = !forceRefresh, isRefreshing = forceRefresh)
        viewModelScope.launch {
            kotlin.runCatching {
                this@MainViewModel.sdk.getLaunches(forceRefresh)
            }.onSuccess {
                uiState = MainUiState(rocketList = it)
            }.onFailure {
                uiState = uiState.copy(
                    error = it.message ?: "Something went wrong",
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }
}