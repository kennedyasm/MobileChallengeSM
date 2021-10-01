package com.challenges.mobilechallengesm.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.challenges.mobilechallengesm.databinding.FragmentSearchBeersDialogBinding
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.ui.adapters.BeersSearchAdapter
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import com.challenges.mobilechallengesm.utils.Output
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBeersDialogFragment : Fragment() {

    private lateinit var binding: FragmentSearchBeersDialogBinding

    private lateinit var mAdapter: BeersSearchAdapter

    private val viewModel: BeersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBeersDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = BeersSearchAdapter()
        binding.list.adapter = mAdapter
        viewModel.filterBeers.observe(viewLifecycleOwner, ::validateOutputData)
    }

    private fun validateOutputData(output: Output<List<BeerDto>>) = with(binding) {
        when (output) {
            is Output.Success -> mAdapter.submitList(output.data)
                .also { linearProgressIndicator.hide() }
            is Output.Loading -> linearProgressIndicator.show()
            is Output.Error -> linearProgressIndicator.hide()
        }
    }


}

