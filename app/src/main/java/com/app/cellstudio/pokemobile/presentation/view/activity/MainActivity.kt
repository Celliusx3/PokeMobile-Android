package com.app.cellstudio.pokemobile.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import com.app.cellstudio.domain.entity.Page
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.di.modules.MainModule
import com.app.cellstudio.pokemobile.interactor.viewmodel.MainViewModel
import com.app.cellstudio.pokemobile.presentation.view.adapter.MainPagerAdapter
import com.app.cellstudio.pokemobile.presentation.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit
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

        setupSearchCloseOnClickListener()
        setupEditTextOnTextChangedListener()

        val disposable = mainViewModel.getFragmentPages()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    this.fragmentPages = it
                    setupBottomNavigationView(fragmentPages)
                    setupMainPagerAdapter(fragmentPages)
                    setPage(Page.HomePage.pageId)
                }

        compositeDisposable.add(disposable)
    }

    override fun onResume() {
        super.onResume()
        subscribeSearchQueryInputText()
    }

    private fun setupBottomNavigationView(pages: List<Page>?) {
        if (bnvMain == null)
            return

        for (page in pages!!) {
            when (page) {
                Page.HomePage -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_home_white_24dp)
                Page.SearchPage -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_search_white_24dp)
                Page.SettingsPage -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_settings_white_24dp)
                else -> bnvMain.menu.add(Menu.NONE, page.pageId, Menu.NONE,
                        page.title).setIcon(R.drawable.ic_broken_image_24dp)
            }
        }

        bnvMain.setOnNavigationItemSelectedListener { item ->
            this.setPage(item.itemId)
            setupSearchHeader(item.itemId)
            true
        }
    }

    private fun setPage(pageId: Int) {
        val pagePosition = mainPagerAdapter.getPagePositionById(pageId)
        dsvpMain.currentItem = pagePosition
        setToolbarTitle(mainPagerAdapter.getPageTitle(pagePosition).toString())
    }

    private fun setupMainPagerAdapter(pages: List<Page>) {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager, pages)
        dsvpMain.offscreenPageLimit = pages.size
        dsvpMain.adapter = mainPagerAdapter
    }

    /**
     * Functions for search fragment
     */
    private fun setupSearchHeader(pageId: Int) {
        when (pageId) {
            Page.SearchPage.pageId -> rlSearchHeader.visibility = View.VISIBLE
            else -> rlSearchHeader.visibility = View.GONE
        }
    }

    private fun setupSearchCloseOnClickListener () {
        btnSearchClose.setOnClickListener { etSearchInput.setText("") }
    }

    private fun setupEditTextOnTextChangedListener() {
        etSearchInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.updateSearchQueryInputText(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun subscribeSearchQueryInputText() {
        val disposable = mainViewModel.getSearchQueryInputText()
                .compose(bindToLifecycle())
                .observeOn(getUiScheduler())
                .throttleWithTimeout(1000, TimeUnit.MILLISECONDS, getUiScheduler())
                .filter { query -> query.isNotBlank() }
                .subscribe({ query ->
                    val searchFragment = mainPagerAdapter.getItem(dsvpMain.currentItem) as SearchFragment
                    searchFragment.onSearchQueryUpdated(query)
                }, { throwable ->
                    throwable.printStackTrace()
                })

        compositeDisposable.add(disposable)
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
