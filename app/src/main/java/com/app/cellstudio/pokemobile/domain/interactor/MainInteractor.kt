package com.app.cellstudio.pokemobile.domain.interactor

import com.app.cellstudio.pokemobile.domain.entity.Page
import io.reactivex.Observable

interface MainInteractor {
    fun getFragmentPages(): Observable<List<Page>>
}
