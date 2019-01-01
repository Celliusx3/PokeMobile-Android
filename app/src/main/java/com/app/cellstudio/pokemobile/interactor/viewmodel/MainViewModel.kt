package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel

import com.app.cellstudio.domain.entity.Page
import io.reactivex.Observable

interface MainViewModel : ViewModel {
    fun getFragmentPages(): Observable<List<Page>>
}
