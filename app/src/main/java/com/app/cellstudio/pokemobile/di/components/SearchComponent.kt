package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.SearchModule
import com.app.cellstudio.pokemobile.presentation.view.fragment.SearchFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(searchFragment: SearchFragment)
}
