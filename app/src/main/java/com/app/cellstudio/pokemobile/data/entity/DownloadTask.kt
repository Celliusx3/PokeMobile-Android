package com.app.cellstudio.pokemobile.data.entity

class DownloadTask {
    var imageUrl: String? = null
    var downloadStatus = Status.QUEUE
    var downloadProgress: Float = 0.toFloat()
    var title: String? = null

    enum class Status(val status: String) {
        QUEUE("queue"),
        COMPLETED("completed"),
        PAUSED("paused"),
        DOWNLOADING("downloading"),
        NOT_DOWNLOADED("not_downloaded")
    }
}