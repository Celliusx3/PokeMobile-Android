package com.app.cellstudio.pokemobile.interactor.viewmodel

import com.app.cellstudio.domain.entity.Page
import io.reactivex.Observable

interface MainViewModel : ViewModel {
    fun getFragmentPages(): Observable<List<Page>>
    fun getSearchQueryInputText(): Observable<String>
    fun updateSearchQueryInputText(query: String)
}
