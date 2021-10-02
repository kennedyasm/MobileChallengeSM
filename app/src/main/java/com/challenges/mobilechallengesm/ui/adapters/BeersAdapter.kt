package com.challenges.mobilechallengesm.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.ui.holders.BeersHolder

class BeersAdapter(private val listener: (BeerDto) -> Unit) :
    PagingDataAdapter<BeerDto, BeersHolder>(CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersHolder =
        BeersHolder.from(parent, listener)

    override fun onBindViewHolder(holder: BeersHolder, position: Int) =
        holder.bind(getItem(position))


    companion object {

        val CALLBACK = object : DiffUtil.ItemCallback<BeerDto>() {
            override fun areItemsTheSame(oldItem: BeerDto, newItem: BeerDto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BeerDto, newItem: BeerDto): Boolean =
                oldItem == newItem
        }

    }


}