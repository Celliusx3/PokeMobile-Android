package com.app.cellstudio.pokemobile.interactor.viewmodel.impl

import com.app.cellstudio.domain.entity.Page
import com.app.cellstudio.domain.interactor.MainInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModelImpl(private val mainInteractor: MainInteractor,
                        scheduler: BaseSchedulerProvider) :
        BaseViewModel(scheduler), MainViewModel {

    private val inputText = PublishSubject.create<String>()

    override fun updateSearchQueryInputText(query: String) {
        inputText.onNext(query)
    }

    override fun getSearchQueryInputText(): Observable<String> {
        return inputText
    }

    override fun getFragmentPages(): Observable<List<Page>> {
        return this.mainInteractor.getFragmentPages()
    }
}
