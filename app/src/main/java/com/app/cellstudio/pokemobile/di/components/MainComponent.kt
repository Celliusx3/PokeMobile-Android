package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.MainModule
import com.app.cellstudio.pokemobile.presentation.view.activity.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
