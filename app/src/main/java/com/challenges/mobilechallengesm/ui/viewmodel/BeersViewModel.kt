package com.challenges.mobilechallengesm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.challenges.mobilechallengesm.data.repository.BeersRepository
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.dto.toBeerDto
import com.challenges.mobilechallengesm.utils.Output
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BeersViewModel @Inject constructor(
    private val beersRepository: BeersRepository
) : ViewModel() {

    val beersPaging = beersRepository.getBeers().map { paging ->
        paging.map { it.toBeerDto() }
    }.cachedIn(viewModelScope)


    private val mFilterBeers = MutableLiveData<Output<List<BeerDto>>>()
    val filterBeers: LiveData<Output<List<BeerDto>>>
        get() = mFilterBeers

    fun searchBeersByQuery(query: String?) {
        mFilterBeers.value = Output.Loading
        if (query.isNullOrBlank()) {
            mFilterBeers.value = Output.Success(mutableListOf())
        } else {
            viewModelScope.launch {
                beersRepository.getBeersByQuery(query).catch { exception ->
                    mFilterBeers.value = Output.Error(exception.message ?: "")
                }.collect {
                    mFilterBeers.value = Output.Success(it)
                }
            }
        }

    }


}