package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import com.app.cellstudio.pokemobile.domain.interactor.ConfigInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SettingsViewModel

class SettingsViewModelImpl(private val configInteractor: ConfigInteractor,
                          scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), SettingsViewModel {
    override fun onRemoveCacheClicked() {
        configInteractor.clearAllSharedPref()
    }
}