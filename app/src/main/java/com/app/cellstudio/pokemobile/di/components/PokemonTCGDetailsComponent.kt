package com.app.cellstudio.pokemobile.di.components

import com.app.cellstudio.pokemobile.di.modules.PokemonTCGDetailsModule
import com.app.cellstudio.pokemobile.presentation.view.activity.PokemonTCGDetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [PokemonTCGDetailsModule::class])
interface PokemonTCGDetailsComponent {
    fun inject(pokemonTCGDetailsActivity: PokemonTCGDetailsActivity)
}
