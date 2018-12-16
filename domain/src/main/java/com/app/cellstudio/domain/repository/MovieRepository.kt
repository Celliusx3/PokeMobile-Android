package com.app.cellstudio.domain.repository

import com.app.cellstudio.domain.entity.Movie
import io.reactivex.Observable

interface MovieRepository {
    fun getMoviePages(): Observable<List<String>>
    fun getMoviePage(path: String): Observable<List<Movie>>
}