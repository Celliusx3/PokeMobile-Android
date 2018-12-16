package com.app.cellstudio.domain.entity

class Rating(val percentage: Int) {

    fun getPercentageForRating(): Float {
        return percentage.div (20.0f)
    }
}
