package com.app.cellstudio.pokemobile.presentation.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.ActivitySplashBinding
import com.app.cellstudio.pokemobile.di.modules.SplashModule
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SplashViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.ViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun getViewModel(): ViewModel {
        return splashViewModel
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun getRootView(): View {
        return rlSplash
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(SplashModule())
                .inject(this)
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)

        val binding = DataBindingUtil.bind<ActivitySplashBinding>(view!!)
        binding?.viewModel = splashViewModel

        this.getIsNavigatingToMain()

    }

    private fun getIsNavigatingToMain() {
        val disposable = splashViewModel.navigateToMain()
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    if (it) {
                        navigator.navigateToMain(this)
                    }
                }

        compositeDisposable.add(disposable)
    }

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }
}
