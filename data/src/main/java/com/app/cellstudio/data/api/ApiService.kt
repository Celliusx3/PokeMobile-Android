package com.app.cellstudio.data.api

import com.app.cellstudio.data.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiRoutes.SETS)
    fun getSets(): Observable<PokemonTCGSetDataResponseModel>

    @GET(ApiRoutes.CARDS)
    fun getCards(@Query("setCode") setCode: String, @Query("pageSize") pageSize: Int): Observable<PokemonTCGCardDataResponseModel>

    //TODO: Combine both
    @GET(ApiRoutes.CARDS)
    fun searchCards(@Query("name") name: String): Observable<PokemonTCGCardDataResponseModel>

    @GET(ApiRoutes.TYPES)
    fun getTypes() : Observable<PokemonTCGCardTypeDataResponseModel>

    @GET(ApiRoutes.SUBTYPES)
    fun getSubtypes() : Observable<PokemonTCGCardSubtypeDataResponseModel>

    @GET(ApiRoutes.SUPERTYPES)
    fun getSupertypes() : Observable<PokemonTCGCardSupertypeDataResponseModel>
}