package com.rasel.introtokmm.android.di

import com.rasel.introtokmm.shared.SpaceXSDK
import com.rasel.introtokmm.shared.cache.DatabaseDriverFactory
import com.rasel.introtokmm.android.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { SpaceXSDK(DatabaseDriverFactory(androidContext())) }
    viewModel { MainViewModel(get()) }
}
