package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.databinding.ListItemRailBinding
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import io.reactivex.subjects.PublishSubject

class MovieListAdapter(private val movies: MutableList<MoviePresentationModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var loading: Boolean = false
    private val selectedModel = PublishSubject.create<MoviePresentationModel>()

    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemRailBinding

        constructor(view: View) : super(view)

        constructor(binding: ListItemRailBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder?
        val layoutInflater = LayoutInflater.from(parent.context)

        viewHolder = when (viewType) {
            VIEW_TYPE_LOADING -> {
                val v2 = layoutInflater.inflate(R.layout.loading_bar, parent, false)
                LoadingViewHolder(v2)
            }
            VIEW_TYPE_MOVIE -> {
                val binding = DataBindingUtil
                        .inflate<ListItemRailBinding>(layoutInflater, R.layout.list_item_rail, parent, false)
                ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ListItemRailBinding>(layoutInflater, R.layout.list_item_rail, parent, false)
                ViewHolder(binding)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, position: Int) {
        if (baseHolder is ViewHolder) {

            baseHolder.binding.model = movies[position]
            baseHolder.binding.setListener {
                val pos = baseHolder.adapterPosition
                if (pos >= 0) {
                    selectedModel.onNext(movies[pos])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        val size = movies.size
        return if (loading) size + 1 else size
    }

    override fun getItemViewType(position: Int): Int {
        return if (loading && position == itemCount - 1) {
            VIEW_TYPE_LOADING
        } else VIEW_TYPE_MOVIE
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
        if (loading) {
            notifyItemInserted(itemCount - 1)
        } else {
            notifyItemRemoved(itemCount)
        }
    }

    fun updateData(models: List<MoviePresentationModel>) {
        val start = this.movies.size
        this.movies.addAll(models)
        val newItemCount = models.size
        notifyItemRangeInserted(start, newItemCount)
    }

    fun getSelectedModel(): PublishSubject<MoviePresentationModel> {
        return selectedModel
    }

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_LOADING = 1
    }
}
