package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.MainViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl.MainViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(provider: BaseSchedulerProvider): MainViewModel {
        return MainViewModelImpl(provider)
    }
}