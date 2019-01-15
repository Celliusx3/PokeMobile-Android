package com.app.cellstudio.pokemobile.data.repository.impl

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.data.api.ApiService
import com.app.cellstudio.pokemobile.data.http.HttpClient
import com.app.cellstudio.pokemobile.data.mapper.PokemonTCGCardDataEntityMapper
import com.app.cellstudio.pokemobile.data.mapper.PokemonTCGSetDataEntityMapper
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import io.reactivex.Observable

class PokemonTCGRepositoryImpl(private val httpClient: HttpClient) : PokemonTCGRepository {
    private fun getApiService(): ApiService {
        return httpClient.getApiService()
    }

    override fun getAllPokemonTCGSets(): Observable<List<PokemonTCGSet>> {
        return getApiService().getSets()
                .map { it.sets }
                .flatMap { Observable.fromIterable(it) }
                .map { PokemonTCGSetDataEntityMapper.create(it) }
                .toList()
                .toObservable()
    }

    override fun getPokemonTCGCards(code: String, pageSize: Int): Observable<List<PokemonTCGCard>> {
        return getApiService().getCards(code, pageSize)
                .map{it.cards}
                .flatMap { Observable.fromIterable(it) }
                .map { PokemonTCGCardDataEntityMapper.create(it) }
                .toList()
                .toObservable()
    }

    override fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>> {
        return getApiService().searchCards(name)
                .map{it.cards}
                .flatMap { Observable.fromIterable(it) }
                .map { PokemonTCGCardDataEntityMapper.create(it) }
                .toList()
                .toObservable()
    }

    override fun getPokemonTCGCardSupertypes(): Observable<List<String>> {
        return getApiService().getSupertypes()
                .map{it.supertypes}
    }

    override fun getPokemonTCGCardSubtypes(): Observable<List<String>> {
        return getApiService().getSubtypes()
                .map{it.subtypes}
    }

    override fun getPokemonTCGCardTypes(): Observable<List<String>> {
        return getApiService().getTypes()
                .map{it.types}
    }
}