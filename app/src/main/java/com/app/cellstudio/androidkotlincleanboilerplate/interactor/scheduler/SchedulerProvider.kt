package com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider: BaseSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {
        private var sInstance: BaseSchedulerProvider? = null

        fun getInstance(): BaseSchedulerProvider {
            if (sInstance == null) {
                sInstance = SchedulerProvider()
            }
            return sInstance as BaseSchedulerProvider
        }
    }
}
