package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.databinding.ListItemPokemonTcgCardBinding
import com.app.cellstudio.domain.entity.PokemonTCGCard
import io.reactivex.subjects.PublishSubject

class PokemonTCGCardsAdapter(private val models: MutableList<PokemonTCGCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var loading: Boolean = false
    private val selectedModel = PublishSubject.create<Int>()

    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemPokemonTcgCardBinding

        constructor(view: View) : super(view)
        constructor(binding: ListItemPokemonTcgCardBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?
        val layoutInflater = LayoutInflater.from(parent.context)

        viewHolder = when (viewType) {
            VIEW_TYPE_LOADING -> {
                val v2 = layoutInflater.inflate(R.layout.loading_bar, parent, false)
                LoadingViewHolder(v2)
            }
            VIEW_TYPE_MOVIE -> {
                val binding = DataBindingUtil
                        .inflate<ListItemPokemonTcgCardBinding>(layoutInflater, R.layout.list_item_pokemon_tcg_card, parent, false)
                ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ListItemPokemonTcgCardBinding>(layoutInflater, R.layout.list_item_pokemon_tcg_card, parent, false)
                ViewHolder(binding)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, position: Int) {
        if (baseHolder is ViewHolder) {

            baseHolder.binding.model = models[position]
            baseHolder.binding.setListener {
                val pos = baseHolder.adapterPosition
                if (pos >= 0) {
                    selectedModel.onNext(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        val size = models.size
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

    fun getLoading():Boolean {
        return this.loading
    }

    fun getData(): List<PokemonTCGCard> {
        return models
    }

    fun updateData(models: List<PokemonTCGCard>) {
        val start = this.models.size
        this.models.addAll(models)
        val newItemCount = models.size
        notifyItemRangeInserted(start, newItemCount)
    }

    fun getSelectedModel(): PublishSubject<Int> {
        return selectedModel
    }

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_LOADING = 1
    }
}
