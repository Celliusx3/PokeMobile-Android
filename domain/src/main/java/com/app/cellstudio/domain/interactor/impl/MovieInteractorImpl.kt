package com.app.cellstudio.domain.interactor.impl

import com.app.cellstudio.domain.entity.Movie
import com.app.cellstudio.domain.interactor.MovieInteractor
import com.app.cellstudio.domain.repository.MovieRepository
import io.reactivex.Observable

class MovieInteractorImpl(private val movieRepository: MovieRepository) : MovieInteractor {

    override fun getMoviePages(): Observable<List<String>> {
       return movieRepository.getMoviePages()
    }

    override fun getMoviePage(path: String): Observable<List<Movie>> {
        return movieRepository.getMoviePage(path)
    }
}