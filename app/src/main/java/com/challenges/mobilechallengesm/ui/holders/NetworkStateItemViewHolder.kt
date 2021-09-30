package com.challenges.mobilechallengesm.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.challenges.mobilechallengesm.databinding.NetworkStateItemBinding

class NetworkStateItemViewHolder(
    private val binding: NetworkStateItemBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) = with(binding) {

        progressBar.isVisible = loadState is LoadState.Loading

        retryButton.apply {
            setOnClickListener { retryCallback.invoke() }
            isVisible = loadState is LoadState.Error
        }

        errorMsg.isVisible =
            !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()

        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }

    companion object {

        fun from(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = NetworkStateItemBinding.inflate(inflater, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

    }
}