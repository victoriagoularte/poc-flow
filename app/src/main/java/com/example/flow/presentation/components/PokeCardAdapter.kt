package com.example.flow.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.flow.databinding.CardPokeItemBinding
import com.example.flow.domain.model.Pokemon

typealias OnClickPokeItem = (String) -> Unit

class PokeCardAdapter : ListAdapter<Pokemon, CardPokeViewHolder>(ItemDiff()) {

    var onClickItem: OnClickPokeItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardPokeViewHolder(
            CardPokeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardPokeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickItem?.invoke(item.name) }
    }

    fun setOnClickItemListener(onClick: OnClickPokeItem) {
        onClickItem = onClick
    }

    private class ItemDiff : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon
        ): Boolean {
            return oldItem == newItem
        }
    }
}