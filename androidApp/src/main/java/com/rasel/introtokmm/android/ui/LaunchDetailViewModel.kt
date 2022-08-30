package com.rasel.introtokmm.android.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasel.introtokmm.android.data.LaunchDetailUiState
import com.rasel.introtokmm.shared.SpaceXSDK
import kotlinx.coroutines.launch

class LaunchDetailViewModel(
    handle: SavedStateHandle
): ViewModel() {

    private val launchId: String? = handle["id"]
    var uiState by mutableStateOf(LaunchDetailUiState(isLoading = true))

    fun getDetail(sdk: SpaceXSDK) {
        viewModelScope.launch {
            kotlin.runCatching {
                sdk.getLaunchById(launchId ?: "")
            }.onSuccess {
                uiState = uiState.copy(isLoading = false, detail = it)
            }.onFailure {
                uiState = uiState.copy(isLoading = false, error = it.message)
            }
        }
    }
}