package com.challenges.mobilechallengesm.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.ui.holders.BeersHolder

class BeersSearchAdapter(private val listener: (BeerDto) -> Unit) :
    ListAdapter<BeerDto, BeersHolder>(BeersAdapter.CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersHolder =
        BeersHolder.from(parent, listener)

    override fun onBindViewHolder(holder: BeersHolder, position: Int) =
        holder.bind(getItem(position))
}