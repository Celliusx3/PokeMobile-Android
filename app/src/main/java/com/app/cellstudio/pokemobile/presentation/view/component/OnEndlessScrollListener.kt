package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.component

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class OnEndlessScrollListener : RecyclerView.OnScrollListener() {

    /**
     * The total number of items in the dataset after the last load
     */
    private var previousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        val visibleThreshold = 5
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            onLoadMore()

            loading = true
        }
    }

    abstract fun onLoadMore()
}