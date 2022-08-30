package com.rasel.introtokmm.android.data

import com.rasel.introtokmm.data.LaunchDetail

data class LaunchDetailUiState(
    val isLoading: Boolean = false,
    val detail: LaunchDetail? = null,
    val error: String? = null
)
