package com.challenges.mobilechallengesm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.challenges.mobilechallengesm.databinding.FragmentSearchBeersBinding
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.ui.adapters.BeersSearchAdapter
import com.challenges.mobilechallengesm.ui.dialogs.DetailsBeerDialog
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import com.challenges.mobilechallengesm.utils.Output
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBeersFragment : Fragment() {

    private lateinit var binding: FragmentSearchBeersBinding
    private lateinit var mAdapter: BeersSearchAdapter
    private val viewModel: BeersViewModel by activityViewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return true

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBeersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = BeersSearchAdapter {
            DetailsBeerDialog(requireActivity()).show(it)
        }
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

