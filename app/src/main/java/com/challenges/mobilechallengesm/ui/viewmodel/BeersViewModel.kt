package com.challenges.mobilechallengesm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.challenges.mobilechallengesm.data.repository.BeersRepository
import com.challenges.mobilechallengesm.dto.toBeerDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class BeersViewModel @Inject constructor(
    beersRepository: BeersRepository
) : ViewModel() {

    val beersPaging = beersRepository.getBeers().map { paging ->
        paging.map { it.toBeerDto() }
    }.cachedIn(viewModelScope)

    fun searchByQuery(query: String) {

    }


}