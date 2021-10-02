package com.challenges.mobilechallengesm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.challenges.mobilechallengesm.databinding.FragmentBeersMainBinding
import com.challenges.mobilechallengesm.ui.adapters.BeersAdapter
import com.challenges.mobilechallengesm.ui.adapters.PostLoadStateAdapter
import com.challenges.mobilechallengesm.ui.dialogs.DetailsBeerDialog
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import com.challenges.mobilechallengesm.utils.asMergedLoadStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class BeersMainFragment : Fragment() {

    private lateinit var binding: FragmentBeersMainBinding
    private lateinit var mAdapter: BeersAdapter

    private val viewModel: BeersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeersMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }


    private fun initAdapter() {

        mAdapter = BeersAdapter {
            DetailsBeerDialog(requireActivity()).show(it)
        }

        binding.list.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = PostLoadStateAdapter(mAdapter),
            footer = PostLoadStateAdapter(mAdapter)
        )

        lifecycleScope.launchWhenCreated {
            mAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.beersPaging.collectLatest {
                mAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            mAdapter.loadStateFlow.asMergedLoadStates().distinctUntilChangedBy {
                it.refresh
            }.filter {
                it.refresh is LoadState.NotLoading
            }.collectLatest {
                binding.list.scrollToPosition(0)
            }
        }

    }

    private fun initSwipeToRefresh() =
        binding.swipeRefresh.setOnRefreshListener { mAdapter.refresh() }
}