package com.app.cellstudio.domain.interactor

import com.app.cellstudio.domain.entity.Movie
import io.reactivex.Observable

interface MovieInteractor {
    fun getMoviePages(): Observable<List<String>>
    fun getMoviePage(path: String): Observable<List<Movie>>
}
