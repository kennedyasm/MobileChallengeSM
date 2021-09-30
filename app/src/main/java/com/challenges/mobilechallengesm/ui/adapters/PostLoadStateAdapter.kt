package com.challenges.mobilechallengesm.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.challenges.mobilechallengesm.ui.holders.NetworkStateItemViewHolder

class PostLoadStateAdapter(private val adapter: BeersAdapter) :
    LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder = NetworkStateItemViewHolder.from(parent) { adapter.retry() }

}