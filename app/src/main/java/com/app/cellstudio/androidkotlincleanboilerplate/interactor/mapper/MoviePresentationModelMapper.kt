package com.app.cellstudio.androidkotlincleanboilerplate.interactor.mapper

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import com.app.cellstudio.domain.entity.Movie

class MoviePresentationModelMapper {

    companion object {
        fun create(movie: Movie): MoviePresentationModel {
            val imagePresentationModel =
                ImagePresentationModelMapper.create(movie.images)
            val ratingPresentationModel =
                RatingPresentationModelMapper.create(movie.rating)

            return MoviePresentationModel(movie.id, movie.title, movie.year, movie.synopsis,
                    movie.runtime, movie.released, movie.trailer, movie.genres,
                    imagePresentationModel, ratingPresentationModel, movie.getYearRuntimeGenres())
        }
    }
}