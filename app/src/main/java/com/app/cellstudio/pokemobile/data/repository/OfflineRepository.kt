package com.app.cellstudio.pokemobile.data.repository

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface OfflineRepository {
    fun getAllPokemonTCGSetsOffline(): Observable<List<PokemonTCGSet>>
    fun setAllPokemonTCGSetsOffline(pokemonTCGSets: List<PokemonTCGSet>)

    fun getPokemonTCGCardsOffline(code: String): Observable<List<PokemonTCGCard>>
    fun setPokemonTCGCardsOffline(pokemonTCGCards: List<PokemonTCGCard>)
}