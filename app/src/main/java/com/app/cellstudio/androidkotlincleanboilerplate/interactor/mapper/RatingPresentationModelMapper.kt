package com.app.cellstudio.androidkotlincleanboilerplate.interactor.mapper

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.RatingPresentationModel
import com.app.cellstudio.domain.entity.Rating

class RatingPresentationModelMapper {
    companion object {
        fun create(rating: Rating): RatingPresentationModel {
            return RatingPresentationModel(rating.getPercentageForRating())
        }
    }
}