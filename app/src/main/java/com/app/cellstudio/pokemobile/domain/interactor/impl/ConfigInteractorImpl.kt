package com.app.cellstudio.pokemobile.domain.interactor.impl

import com.app.cellstudio.pokemobile.data.repository.ConfigRepository
import com.app.cellstudio.pokemobile.domain.interactor.ConfigInteractor

class ConfigInteractorImpl(private val configRepository: ConfigRepository) : ConfigInteractor {
    override fun clearAllSharedPref() {
        configRepository.clearAllSharedPref()
    }
}