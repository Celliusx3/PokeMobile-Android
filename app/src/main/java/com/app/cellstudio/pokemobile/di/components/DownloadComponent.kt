package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.DownloadModule
import com.app.cellstudio.pokemobile.presentation.service.DownloadService
import dagger.Subcomponent

@Subcomponent(modules = [DownloadModule::class])
interface DownloadComponent {
    fun inject(service: DownloadService)
}