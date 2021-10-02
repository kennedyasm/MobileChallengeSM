package com.challenges.mobilechallengesm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import com.challenges.mobilechallengesm.data.repository.BeersRepository
import com.challenges.mobilechallengesm.dto.BeerDto
import com.challenges.mobilechallengesm.dto.toBeerDto
import com.challenges.mobilechallengesm.utils.Output
import com.challenges.mobilechallengesm.utils.errorMessage
import com.challenges.mobilechallengesm.utils.listBeersHardcode
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.IOException

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class BeersViewModelTest {


    @MockK(relaxed = true)
    lateinit var beersViewModel: BeersViewModel

    @MockK(relaxed = true)
    lateinit var beersRepository: BeersRepository

    private val testDispatcher = TestCoroutineDispatcher()

    private val filters = listOf("a", "z")

    private val listBeers: List<BeerItem>
        get() = listBeersHardcode

    private val testErrorMessage: String
        get() = errorMessage

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var filterBeersObserver: Observer<Output<List<BeerDto>>>

    private lateinit var pagingState: PagingState<Int, BeerEntity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        beersViewModel = spyk(BeersViewModel(beersRepository))
        beersViewModel.filterBeers.observeForever(filterBeersObserver)
        pagingState = spyk(
            PagingState(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 10
            )
        )

    }

    @Test
    fun `get beers list by query successfully`() =
        testDispatcher.runBlockingTest {

            val fakeList = listBeers.filter {
                it.name.contains(filters[0])
            }.map { it.toBeerDto() }

            coEvery { beersRepository.getBeersByQuery(filters[0]) } returns flow {
                emit(fakeList)
            }

            val resultRepository = beersRepository.getBeersByQuery(filters[0]).last()
            beersViewModel.searchBeersByQuery(filters[0])

            verifySequence {
                filterBeersObserver.onChanged(Output.Loading)
                filterBeersObserver.onChanged(Output.Success(resultRepository))
            }
        }

    @Test
    fun `get beers list by query failed`() =
        testDispatcher.runBlockingTest {

            coEvery { beersRepository.getBeersByQuery(filters[0]) } returns flow {
                throw IOException(testErrorMessage)
            }

            beersViewModel.searchBeersByQuery(filters[0])

            verifySequence {
                filterBeersObserver.onChanged(Output.Loading)
                filterBeersObserver.onChanged(Output.Error(testErrorMessage))
            }

        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        beersViewModel.filterBeers.removeObserver(filterBeersObserver)
    }

}