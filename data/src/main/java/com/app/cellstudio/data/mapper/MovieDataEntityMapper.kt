package com.app.cellstudio.data.mapper

import com.app.cellstudio.data.entity.MovieDataModel
import com.app.cellstudio.domain.entity.Movie

class MovieDataEntityMapper {

    companion object {
        fun create (movieDataModel: MovieDataModel): Movie {
            val image = ImageDataEntityMapper.create(movieDataModel.images)
            val rating = RatingDataEntityMapper.create(movieDataModel.rating)

            return Movie(movieDataModel._id ?: "", movieDataModel.title ?: "", movieDataModel.year?: "", movieDataModel.synopsis ?: "",
                    movieDataModel.runtime ?: "", movieDataModel.released ?: 0, movieDataModel.trailer ?: "",
                     movieDataModel.genres ?: emptyList(), image, rating)
        }
    }
}