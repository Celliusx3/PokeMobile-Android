package com.app.cellstudio.domain.interactor

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGInteractor {
    fun getAllPokemonTCGSets(isReverseOrder: Boolean): Observable<List<PokemonTCGSet>>
    fun getPokemonTCGCards(code: String, page: Int): Observable<List<PokemonTCGCard>>

    fun getFilterSeriesToShow(): Observable<List<String>>
    fun getFilterLegalToShow(): Observable<List<String>>

    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
}
