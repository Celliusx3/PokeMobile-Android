package com.app.cellstudio.androidkotlincleanboilerplate.interactor.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
class ImagePresentationModel(val poster: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelImagePresentationModel.CREATOR
    }
}
