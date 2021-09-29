package com.challenges.mobilechallengesm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenges.mobilechallengesm.data.repository.BeersRepository
import com.challenges.mobilechallengesm.dto.BeerDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BeersViewModel @Inject constructor(
    private val beersRepository: BeersRepository
) : ViewModel() {

    private val _beersItems: MutableLiveData<List<BeerDto>> = MutableLiveData()
    val beersItems: LiveData<List<BeerDto>>
        get() = _beersItems

    init {
        getBeers()
    }

    fun getBeers() {

        viewModelScope.launch {
            beersRepository.getBeers().catch { exception ->
                Log.d("kTest -> ", " CATCH::ERROR - ${exception.message}")
            }.collect {
                _beersItems.postValue(it)
            }
        }

    }

}