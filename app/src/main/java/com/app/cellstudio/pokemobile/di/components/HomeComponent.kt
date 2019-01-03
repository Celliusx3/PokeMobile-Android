package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.HomeModule
import com.app.cellstudio.pokemobile.presentation.view.fragment.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}