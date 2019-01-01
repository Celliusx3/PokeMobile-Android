package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.navigation.Navigator
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : RxFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    protected val compositeDisposable = CompositeDisposable()

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected fun getUiScheduler(): Scheduler{
        return scheduler.ui()
    }

    protected fun getIoScheduler(): Scheduler{
        return scheduler.io()
    }

    protected fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    protected open fun onBindView(view: View?) {}

    protected open fun onInject() {}

    protected open fun onBindData(view: View?) {}

    protected fun onGetInputData() {}

}
