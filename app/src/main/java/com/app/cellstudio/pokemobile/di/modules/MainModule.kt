package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.domain.interactor.MainInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.MainViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.impl.MainViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(mainInteractor: MainInteractor, provider: BaseSchedulerProvider): MainViewModel {
        return MainViewModelImpl(mainInteractor, provider)
    }
}