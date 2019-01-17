package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import com.app.cellstudio.pokemobile.domain.entity.Page
import io.reactivex.Observable

interface MainViewModel : ViewModel {
    fun getFragmentPages(): Observable<List<Page>>
    fun getSearchQueryInputText(): Observable<String>
    fun updateSearchQueryInputText(query: String)
}
