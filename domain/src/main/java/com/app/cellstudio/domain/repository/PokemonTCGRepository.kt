package com.app.cellstudio.domain.repository

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGRepository {
    fun getAllPokemonTCGSets(): Observable<List<PokemonTCGSet>>
    fun getPokemonTCGCards(code: String, page: Int, pageSize: Int): Observable<List<PokemonTCGCard>>
    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
}