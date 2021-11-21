package com.app.cellstudio.domain.entity

class Page(val title: String,
           val pageId: Int) {
    companion object {
        val HomePage = Page("Home", 1000)
        val SearchPage = Page ("Search", 1001)
        val SettingsPage = Page("Settings", 5000)
    }
}
