package com.app.cellstudio.pokemobile.data.repository.impl

import com.app.cellstudio.pokemobile.data.api.ApiService
import com.app.cellstudio.pokemobile.data.environment.Environment
import com.app.cellstudio.pokemobile.data.http.HttpClient
import com.app.cellstudio.pokemobile.data.mapper.PokemonTCGCardDataEntityMapper
import com.app.cellstudio.pokemobile.data.mapper.PokemonTCGSetDataEntityMapper
import com.app.cellstudio.pokemobile.data.pref.BasePref
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

class PokemonTCGRepositoryImpl(private val httpClient: HttpClient,
                               private val pref: BasePref,
                               private val environment: Environment) : PokemonTCGRepository {
    private fun getApiService(): ApiService {
        return httpClient.getApiService()
    }

    private fun getPref(): BasePref {
        return pref
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
        val supertypes = pref.getFilterSupertypes()
        val nextUpdate = pref.getNextFilterSupertypesUpdate()
        val currentMillis = System.currentTimeMillis()

        return if (supertypes != null && currentMillis < nextUpdate) {
            Observable.just(supertypes.supertypes)
        } else {
            getApiService().getSupertypes()
                    .map { getPref().setFilterSupertypes(it)
                        getPref().setNextFilterSupertypesUpdate(currentMillis + environment.getNextUpdateDuration())
                        return@map it
                    }
                    .map{it.supertypes}
        }
    }

    override fun getPokemonTCGCardSubtypes(): Observable<List<String>> {
        val subtypes = pref.getFilterSubtypes()
        val nextUpdate = pref.getNextFilterSubtypesUpdate()
        val currentMillis = System.currentTimeMillis()

        return if (subtypes != null && currentMillis < nextUpdate) {
            Observable.just(subtypes.subtypes)
        } else {
            getApiService().getSubtypes()
                    .map { getPref().setFilterSubtypes(it)
                        getPref().setNextFilterSubtypesUpdate(currentMillis + environment.getNextUpdateDuration())
                        return@map it
                    }
                    .map{it.subtypes}
        }
    }

    override fun getPokemonTCGCardTypes(): Observable<List<String>> {
        val types = pref.getFilterTypes()
        val nextUpdate = pref.getNextFilterTypesUpdate()
        val currentMillis = System.currentTimeMillis()

        return if (types != null && currentMillis < nextUpdate) {
            Observable.just(types.types)
        } else {
            getApiService().getTypes()
                    .map { getPref().setFilterTypes(it)
                        getPref().setNextFilterTypesUpdate(currentMillis + environment.getNextUpdateDuration())
                        return@map it
                    }
                    .map{it.types}
        }
    }
}