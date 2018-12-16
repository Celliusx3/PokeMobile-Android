package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.app.cellstudio.androidkotlincleanboilerplate.BaseApplication
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.MainModule
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MainViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter.MainPagerAdapter
import com.app.cellstudio.domain.entity.Page
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var mainPagerAdapter: MainPagerAdapter
    private lateinit var fragmentPages: List<Page>

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun getRootView(): View {
        return rlMain
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(MainModule())
                .inject(this)
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)

        val disposable = mainViewModel.getFragmentPages()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    this.fragmentPages = it
                    setupBottomNavigationView(fragmentPages)
                    setupMainPagerAdapter(fragmentPages)
                }

        compositeDisposable.add(disposable)
    }

    private fun setupBottomNavigationView(pages: List<Page>?) {
        if (bnvMain == null)
            return

        for (page in pages!!) {
            when (page) {
                Page.HomePage -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_home_white_24dp)
                Page.SettingsPage -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_settings_white_24dp)
                else -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_broken_image_24dp)
            }
        }

        bnvMain.setOnNavigationItemSelectedListener { item ->
            this.setPage(item.itemId)
            true
        }
    }

    private fun setPage(pageId: Int) {
        dsvpMain.currentItem = mainPagerAdapter.getPagePositionById(pageId)
    }

    private fun setupMainPagerAdapter(pages: List<Page>) {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager, pages)
        dsvpMain.adapter = mainPagerAdapter
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
