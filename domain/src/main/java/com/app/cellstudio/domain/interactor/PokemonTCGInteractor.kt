package com.app.cellstudio.domain.interactor

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGInteractor {
    fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>>
    fun getPokemonTCGCards(code: String, page: Int): Observable<List<PokemonTCGCard>>
    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
}
