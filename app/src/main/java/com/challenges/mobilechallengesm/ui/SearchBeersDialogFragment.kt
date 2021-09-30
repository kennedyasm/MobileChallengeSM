package com.challenges.mobilechallengesm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.challenges.mobilechallengesm.databinding.FragmentSearchBeersDialogBinding
import com.challenges.mobilechallengesm.ui.viewmodel.BeersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBeersDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentSearchBeersDialogBinding

    companion object {
        const val TAG = "SearchBeersDialogFragment"
    }

    private val viewModel: BeersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBeersDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}

