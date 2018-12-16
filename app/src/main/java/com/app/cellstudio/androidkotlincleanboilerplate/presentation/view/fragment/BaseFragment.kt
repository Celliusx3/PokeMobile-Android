package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.navigation.Navigator
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

abstract class BaseFragment : RxFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    private val currentUserVisibleHint = BehaviorSubject.createDefault(this.userVisibleHint)
    protected val compositeDisposable = CompositeDisposable()

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected fun getUiScheduler(): Scheduler{
        return scheduler.ui()
    }

    protected fun getIoScheduler(): Scheduler{
        return scheduler.io()
    }

    protected fun getTitle(): String {
        return ""
    }

    protected fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
    }

    fun getCurrentUserVisibleHint(): Observable<Boolean> {
        return currentUserVisibleHint
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(getLayoutResource(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onGetInputData()
        onInject()
        onBindView(view)
        onBindData(view)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    protected open fun onBindView(view: View?) {}

    protected open fun onInject() {}

    protected open fun onBindData(view: View?) {}

    protected fun onGetInputData() {}

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        currentUserVisibleHint.onNext(isVisibleToUser)
    }

}
