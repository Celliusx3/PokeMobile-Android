package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.SplashModule
import com.app.cellstudio.pokemobile.presentation.view.activity.SplashActivity
import dagger.Subcomponent

@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
