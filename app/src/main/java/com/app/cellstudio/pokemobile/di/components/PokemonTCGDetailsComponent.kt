package com.app.cellstudio.androidkotlincleanboilerplate.di.components

import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.PokemonTCGDetailsModule
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity.PokemonTCGDetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [PokemonTCGDetailsModule::class])
interface PokemonTCGDetailsComponent {
    fun inject(pokemonTCGDetailsActivity: PokemonTCGDetailsActivity)
}
