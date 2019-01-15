package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import com.app.cellstudio.pokemobile.domain.entity.Page
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

class MainViewModelImpl(scheduler: BaseSchedulerProvider) :
        BaseViewModel(scheduler), MainViewModel {

    private val inputText = PublishSubject.create<String>()

    override fun updateSearchQueryInputText(query: String) {
        inputText.onNext(query)
    }

    override fun getSearchQueryInputText(): Observable<String> {
        return inputText
    }

    override fun getFragmentPages(): Observable<List<Page>> {
        val fragmentPages = ArrayList<Page>()

        fragmentPages.add(Page.HomePage)
        fragmentPages.add(Page.SearchPage)
        fragmentPages.add(Page.SettingsPage)

        return Observable.just<List<Page>>(fragmentPages)
    }
}
