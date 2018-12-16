package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import io.reactivex.Observable

interface HomeViewModel : ViewModel {
    fun getMoviePages(): Observable<List<String>>
    fun getIsLoading(): Observable<Boolean>
    fun getMoviePage(path: String): Observable<List<MoviePresentationModel>>
}