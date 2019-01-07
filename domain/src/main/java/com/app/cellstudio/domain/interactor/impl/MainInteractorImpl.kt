package com.app.cellstudio.domain.interactor.impl

import com.app.cellstudio.domain.entity.Page
import com.app.cellstudio.domain.interactor.MainInteractor
import io.reactivex.Observable
import java.util.*

class MainInteractorImpl : MainInteractor {

    override fun getFragmentPages(): Observable<List<Page>> {
        val fragmentPages = ArrayList<Page>()

        fragmentPages.add(Page.HomePage)
        fragmentPages.add(Page.SearchPage)
        fragmentPages.add(Page.SettingsPage)

        return Observable.just<List<Page>>(fragmentPages)
    }
}