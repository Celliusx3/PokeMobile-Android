package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.domain.interactor.ConfigInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SettingsViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl.SettingsViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsViewModel(configInteractor: ConfigInteractor, provider: BaseSchedulerProvider): SettingsViewModel {
        return SettingsViewModelImpl(configInteractor, provider)
    }
}