package com.app.cellstudio.pokemobile.presentation.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.app.cellstudio.pokemobile.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_settings
    }

    override fun onInject() {}

    override fun onBindData(view: View?) {
        super.onBindData(view)
        rlSettingsCredits.setOnClickListener { onCreditsClicked() }
    }

    private fun onCreditsClicked() {
        val alertDialog = AlertDialog.Builder(context)
                .setMessage("Thank you for supporting us.")
                .setPositiveButton("OK") { dialog, _ -> dialog?.dismiss() }
                .create()
        alertDialog.show()
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