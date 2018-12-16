package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.mapper.MoviePresentationModelMapper
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.domain.interactor.MovieInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeViewModelImpl(private val movieInteractor: MovieInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), HomeViewModel {

    private val isLoading = PublishSubject.create<Boolean>()

    override fun getIsLoading(): Observable<Boolean> {
        return isLoading
    }

    override fun getMoviePages(): Observable<List<String>> {
        isLoading.onNext(true)
        return movieInteractor.getMoviePages()
                .doFinally { isLoading.onNext(false) }
    }

    override fun getMoviePage(path: String): Observable<List<MoviePresentationModel>> {
        isLoading.onNext(true)
        return movieInteractor.getMoviePage(path)
                .flatMap { Observable.fromIterable(it) }
                .map {MoviePresentationModelMapper.create(it)}
                .toList()
                .toObservable()
                .doFinally { isLoading.onNext(false) }
    }
}