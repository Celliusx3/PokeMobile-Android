package com.app.cellstudio.pokemobile.presentation.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.di.modules.SettingsModule
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SettingsViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.ViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    override fun getViewModel(): ViewModel? {
        return settingsViewModel
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_settings
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(SettingsModule())
                .inject(this)
    }

    override fun onBindData(view: View?) {
        super.onBindData(view)
        rlSettingsCredits.setOnClickListener { onCreditsClicked() }
        rlSettingsRemoveCache.setOnClickListener { onRemoveCacheClicked() }
    }

    private fun onCreditsClicked() {
        val alertDialog = AlertDialog.Builder(context)
                .setMessage("Thank you for supporting us.")
                .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
                .create()
        alertDialog.show()
    }

    private fun onRemoveCacheClicked() {
        settingsViewModel.onRemoveCacheClicked()
    }

    companion object {

        private val TAG = SettingsFragment::class.java.simpleName

        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}