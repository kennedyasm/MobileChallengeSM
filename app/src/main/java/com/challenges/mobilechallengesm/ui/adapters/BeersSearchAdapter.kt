package com.challenges.mobilechallengesm.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.ui.holders.BeersHolder

class BeersSearchAdapter : ListAdapter<BeerDto, BeersHolder>(BeersAdapter.CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersHolder =
        BeersHolder.from(parent)

    override fun onBindViewHolder(holder: BeersHolder, position: Int) =
        holder.bind(getItem(position))
}