package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MainViewModel
import com.app.cellstudio.domain.entity.Page
import com.app.cellstudio.domain.interactor.MainInteractor
import io.reactivex.Observable

class MainViewModelImpl(private val mainInteractor: MainInteractor,
                        scheduler: BaseSchedulerProvider) :
        BaseViewModel(scheduler), MainViewModel {

    override fun getFragmentPages(): Observable<List<Page>> {
        return this.mainInteractor.getFragmentPages()
    }
}
