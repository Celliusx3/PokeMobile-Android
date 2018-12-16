package com.app.cellstudio.androidkotlincleanboilerplate.interactor.mapper

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.ImagePresentationModel
import com.app.cellstudio.domain.entity.Image

class ImagePresentationModelMapper {

    companion object {
        fun create(image: Image): ImagePresentationModel {
            return ImagePresentationModel(image.poster)
        }
    }
}
