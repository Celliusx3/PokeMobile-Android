package com.app.cellstudio.androidkotlincleanboilerplate.interactor.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
class RatingPresentationModel(val percentage: Float) : PaperParcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelRatingPresentationModel.CREATOR
    }
}