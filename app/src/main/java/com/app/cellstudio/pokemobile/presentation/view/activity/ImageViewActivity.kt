package com.app.cellstudio.pokemobile.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.presentation.view.adapter.ImagePagerAdapter
import kotlinx.android.synthetic.main.activity_image_view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class ImageViewActivity : BaseActivity() {

    private lateinit var imageUrls: List<String>
    private var initialPos: Int = 0
    private lateinit var imagePagerAdapter: ImagePagerAdapter

    override fun getLayoutResource(): Int {
        return R.layout.activity_image_view
    }

    override fun getRootView(): View {
        return rlImageViewer
    }

    override fun getToolbarTitle(): String {
        return ""
    }

    override fun onInject() {
//        BaseApplication.getInstance()
//                .getApplicationComponent()
//                .plus(PokemonTCGDetailsModule())
//                .inject(this)
    }

    override fun onBindView() {
        super.onBindView()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
//        sblImageViewer.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
//        sblImageViewer.setDragDirectMode(SwipeBackLayout.DragDirectMode.VERTICAL)
//        sblImageViewer.setOnSwipeBackListener(object: SwipeBackLayout.SwipeBackListener {
//            override fun onViewPositionChanged(fractionAnchor: Float, fractionScreen: Float) {
//                sblImageViewer.background.alpha = ((1 - fractionScreen) * 255).toInt()
//            }
//
//        })
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)
        setupImagePagerAdapter(imageUrls, initialPos)
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        if (intent != null) {
            imageUrls = intent.getStringArrayListExtra(EXTRA_IMAGE_URLS)
            initialPos = intent.getIntExtra(EXTRA_IMAGE_POS, 0)
        }
    }

    private fun setupImagePagerAdapter(imageUrls: List<String>, initialPos: Int) {
        imagePagerAdapter = ImagePagerAdapter(imageUrls)
        vpImageViewer.adapter = imagePagerAdapter
        vpImageViewer.currentItem = initialPos
    }

    companion object {

        private val TAG = ImageViewActivity::class.java.simpleName

        private const val EXTRA_IMAGE_URLS = "EXTRA_IMAGE_URLS"
        private const val EXTRA_IMAGE_POS = "EXTRA_IMAGE_POS"

        fun getCallingIntent(context: Context, position: Int, imageUrls: List<String>): Intent {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putStringArrayListExtra(EXTRA_IMAGE_URLS, imageUrls as ArrayList<String>)
            intent.putExtra(EXTRA_IMAGE_POS, position)
            return intent
        }
    }
}