package com.app.cellstudio.data.repository

import com.app.cellstudio.data.api.ApiService
import com.app.cellstudio.data.http.HttpClient
import com.app.cellstudio.data.mapper.PokemonTCGCardDataEntityMapper
import com.app.cellstudio.data.mapper.PokemonTCGSetDataEntityMapper
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.domain.repository.PokemonTCGRepository
import io.reactivex.Observable

class PokemonTCGRepositoryImpl(private val httpClient: HttpClient) : PokemonTCGRepository {
    private fun getApiService(): ApiService {
        return httpClient.getApiService()
    }

    override fun getPokemonTCGSets(page: Int, pageSize: Int): Observable<List<PokemonTCGSet>> {
        return getApiService().getSets(page, pageSize)
                .map { it.sets }
                .flatMap { Observable.fromIterable(it) }
                .map { PokemonTCGSetDataEntityMapper.create(it) }
                .toList()
                .toObservable()
    }

    override fun getPokemonTCGCards(code: String, page: Int, pageSize: Int): Observable<List<PokemonTCGCard>> {
        return getApiService().getCards(code, page, pageSize)
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
}