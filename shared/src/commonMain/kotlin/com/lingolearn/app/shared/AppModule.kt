package com.lingolearn.app.shared

import com.lingolearn.app.shared.core.data.repository.ProgressRepository
import com.lingolearn.app.shared.core.data.repository.SettingsRepository
import com.lingolearn.app.shared.core.data.repository.WordRepository
import com.lingolearn.app.shared.feature.home.HomeViewModel
import com.lingolearn.app.shared.feature.learning.LearningViewModel
import com.lingolearn.app.shared.feature.practice.PracticeViewModel
import com.lingolearn.app.shared.feature.progress.ProgressViewModel
import com.lingolearn.app.shared.feature.settings.SettingsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module = module {
    single { WordRepository() }
    single { ProgressRepository() }
    single { SettingsRepository() }
}

val viewModelModule: Module = module {
    factory { HomeViewModel(get(), get(), get()) }
    factory { LearningViewModel(get(), get()) }
    factory { PracticeViewModel(get()) }
    factory { ProgressViewModel(get(), get()) }
    factory { SettingsViewModel(get(), get()) }
}

val appModules = listOf(dataModule, viewModelModule)
