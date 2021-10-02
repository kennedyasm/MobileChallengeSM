package com.challenges.mobilechallengesm.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenges.mobilechallengesm.databinding.BeerItemBinding
import com.challenges.mobilechallengesm.dto.BeerDto

class BeersHolder(private val binding: BeerItemBinding, private val listener: (BeerDto) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mItem: BeerDto?) = with(binding) {
        mItem?.let {
            item = mItem
            itemContainer.setOnClickListener { listener.invoke(mItem) }
        }
        executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup, listener: (BeerDto) -> Unit): BeersHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = BeerItemBinding.inflate(inflater, parent, false)
            return BeersHolder(view, listener)
        }

    }

}