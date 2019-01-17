package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.SettingsModule
import com.app.cellstudio.pokemobile.presentation.view.fragment.SettingsFragment
import dagger.Subcomponent

@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {
    fun inject(settingsFragment: SettingsFragment)
}
