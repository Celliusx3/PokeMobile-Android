package com.app.cellstudio.pokemobile.presentation.util.image

class BaseImageLoader {

    companion object {
        private var sInstance: ImageLoader? = null
        fun getInstance (): ImageLoader {
            if (sInstance == null) {
                sInstance = PicassoImageLoader()
            }
            return sInstance as ImageLoader
        }
    }
}
