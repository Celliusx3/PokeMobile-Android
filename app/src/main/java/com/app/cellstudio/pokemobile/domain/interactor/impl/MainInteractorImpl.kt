package com.app.cellstudio.pokemobile.domain.interactor.impl

import com.app.cellstudio.pokemobile.domain.interactor.MainInteractor
import com.app.cellstudio.pokemobile.domain.entity.Page
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