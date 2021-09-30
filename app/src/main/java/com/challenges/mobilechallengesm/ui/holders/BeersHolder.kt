package com.challenges.mobilechallengesm.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenges.mobilechallengesm.databinding.BeerItemBinding
import com.challenges.mobilechallengesm.dto.BeerDto

class BeersHolder(private val binding: BeerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mItem: BeerDto?) = with(binding) {
        item = mItem
        executePendingBindings()
    }

    companion object {

        fun from(parent: ViewGroup): BeersHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = BeerItemBinding.inflate(inflater, parent, false)
            return BeersHolder(view)
        }

    }

}