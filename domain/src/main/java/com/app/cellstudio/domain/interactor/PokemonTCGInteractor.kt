package com.app.cellstudio.domain.interactor

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGInteractor {
    fun getAllPokemonTCGSets(isReverseOrder: Boolean): Observable<List<PokemonTCGSet>>
    fun getFilterSeriesToShow(): Observable<List<String>>
    fun getFilterLegalToShow(): Observable<List<String>>

    fun getAllPokemonTCGCards(code: String): Observable<List<PokemonTCGCard>>
    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
    fun getFilterTypesToShow(): Observable<List<String>>
    fun getFilterSubtypesToShow(): Observable<List<String>>
    fun getFilterSupertypesToShow(): Observable<List<String>>


}
