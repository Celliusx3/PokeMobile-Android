package com.app.cellstudio.pokemobile.domain.interactor

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGInteractor {
    fun getAllPokemonTCGSets(isReverseOrder: Boolean, isConnected: Boolean): Observable<List<PokemonTCGSet>>
    fun getFilterSeriesToShow(): Observable<List<String>>
    fun getFilterLegalToShow(): Observable<List<String>>

    fun getAllPokemonTCGCards(code: String): Observable<List<PokemonTCGCard>>
    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
    fun getFilterTypesToShow(): Observable<List<String>>
    fun getFilterSubtypesToShow(): Observable<List<String>>
    fun getFilterSupertypesToShow(): Observable<List<String>>
}
