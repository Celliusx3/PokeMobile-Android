package com.app.cellstudio.data.mapper

import com.app.cellstudio.data.entity.RatingDataModel
import com.app.cellstudio.domain.entity.Rating

class RatingDataEntityMapper {

    companion object {
        fun create (ratingDataModel: RatingDataModel?): Rating {
            if (ratingDataModel != null)
                return Rating(ratingDataModel.percentage ?: 0)
            return Rating(0)
        }
    }
}