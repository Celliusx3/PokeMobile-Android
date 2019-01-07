package com.app.cellstudio.pokemobile.interactor.viewmodel

import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected val scheduler: BaseSchedulerProvider): ViewModel {

    protected lateinit var subscriptions: CompositeDisposable

    override fun onCreateView() {
        subscriptions = CompositeDisposable()
    }

    override fun onAttachView() {
        if (subscriptions.isDisposed)
            subscriptions = CompositeDisposable()
    }

    override fun onDetachView() {
        subscriptions.dispose()
    }

}
