package com.app.cellstudio.data.repository

import com.app.cellstudio.data.api.ApiService
import com.app.cellstudio.data.http.HttpClient
import com.app.cellstudio.data.mapper.MovieDataEntityMapper
import com.app.cellstudio.domain.entity.Movie
import com.app.cellstudio.domain.repository.MovieRepository
import io.reactivex.Observable

class MovieRepositoryImpl(private val httpClient: HttpClient) : MovieRepository {

    private fun getApiService(): ApiService {
        return httpClient.getApiService()
    }

    override fun getMoviePages(): Observable<List<String>> {
        return getApiService().getMoviePages()
    }

    override fun getMoviePage(path: String): Observable<List<Movie>> {
        return getApiService().getMoviePage(path)
                .flatMap { Observable.fromIterable(it) }
                .map { MovieDataEntityMapper.create(it) }
                .toList()
                .toObservable()
    }
}