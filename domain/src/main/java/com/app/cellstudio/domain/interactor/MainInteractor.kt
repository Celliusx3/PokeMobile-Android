package com.app.cellstudio.domain.interactor

import com.app.cellstudio.domain.entity.Page
import io.reactivex.Observable

interface MainInteractor {
    fun getFragmentPages(): Observable<List<Page>>
}
