package com.app.cellstudio.androidkotlincleanboilerplate.interactor.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
class MoviePresentationModel(val id: String,
                             val title: String,
                             val year: String,
                             val synopsis: String,
                             val runtime: String,
                             val released: Int,
                             val trailer: String,
                             val genres: List<String>,
                             val images: ImagePresentationModel,
                             val rating: RatingPresentationModel,
                             val year_runtime_genres: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelMoviePresentationModel.CREATOR
    }
}