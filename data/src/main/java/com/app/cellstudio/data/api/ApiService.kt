package com.app.cellstudio.data.api

import com.app.cellstudio.data.entity.MovieDataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(ApiRoutes.MOVIE)
    fun getMoviePages() : Observable<List<String>>

    @GET("{path}")
    fun getMoviePage(@Path("path") path: String): Observable<List<MovieDataModel>>
}