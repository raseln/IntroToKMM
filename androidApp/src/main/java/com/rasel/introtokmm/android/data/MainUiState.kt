package com.rasel.introtokmm.android.data

import com.rasel.introtokmm.data.RocketLaunch

data class MainUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val rocketList: List<RocketLaunch> = listOf(),
    val error: String? = null
)
