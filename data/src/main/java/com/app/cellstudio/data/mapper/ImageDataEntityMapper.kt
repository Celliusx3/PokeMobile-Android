package com.app.cellstudio.data.mapper

import com.app.cellstudio.data.entity.ImageDataModel
import com.app.cellstudio.domain.entity.Image

class ImageDataEntityMapper {

    companion object {
        fun create (image: ImageDataModel?): Image {
            if (image != null)
                return Image(image.poster ?: "")
            return Image("")
        }
    }
}