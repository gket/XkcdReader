package com.gketdev.xkcdreader.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.databinding.ItemComicBinding
import com.gketdev.xkcdreader.presentation.home.XkcdItemUiState
import javax.inject.Inject
import kotlin.properties.Delegates

class FavoriteAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var onFavClicked: ((Int) -> Unit) = {}
    var onShareClicked: ((String) -> Unit) = {}

    var favorites: List<XkcdItemUiState> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemBinding =
            ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoritesByPositioned = favorites[position]
        holder.bind(favoritesByPositioned)
    }

    override fun getItemCount() = favorites.size


    inner class FavoriteViewHolder(private val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: XkcdItemUiState) {
            binding.textViewTitle.text = item.title
            binding.textViewAlt.text = item.alt
            Glide.with(binding.root).load(item.image).into(binding.imageViewXkcd)
            binding.imageViewFav.setOnClickListener {
                onFavClicked.invoke(item.id)
            }
            binding.imageViewShare.setOnClickListener {
                onShareClicked.invoke(item.image)
            }
        }
    }


}