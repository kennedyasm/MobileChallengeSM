package com.challenges.mobilechallengesm.data.repository

import androidx.paging.*
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.source.LocalDataSource
import com.challenges.mobilechallengesm.data.mediator.PageKeyRemoteMediator
import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import com.challenges.mobilechallengesm.data.remote.source.RemoteDataSource
import com.challenges.mobilechallengesm.dto.toBeerDto
import com.challenges.mobilechallengesm.utils.listBeersHardcode
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class BeersRepositoryImplTest {

    @MockK(relaxed = true)
    lateinit var remoteDataSource: RemoteDataSource

    @MockK(relaxed = true)
    lateinit var localDataSource: LocalDataSource

    @MockK(relaxed = true)
    lateinit var beersRepository: BeersRepository

    private val testDispatcher = TestCoroutineDispatcher()
    private val filters = listOf("a", "z")
    private val listBeers: List<BeerItem>
        get() = listBeersHardcode
    private lateinit var pagingState: PagingState<Int, BeerEntity>
    lateinit var remoteKeyRemoteMediator: PageKeyRemoteMediator
    private val PAGE = "1"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        beersRepository = spyk(
            BeersRepositoryImpl(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                dispatcher = testDispatcher
            )
        )

        pagingState = spyk(
            PagingState(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 10
            )
        )

        remoteKeyRemoteMediator = spyk(PageKeyRemoteMediator(remoteDataSource, localDataSource))


    }

    @Test
    fun `get beers list from remote with refresh load and there is more available data in server`() =
        testDispatcher.runBlockingTest {
            coEvery { remoteDataSource.getBeers(PAGE) } returns listBeersHardcode
            val result = remoteKeyRemoteMediator.load(LoadType.REFRESH, pagingState)
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        }

    @Test
    fun `get beers list from remote and there is not more available data in server successfully`() =
        testDispatcher.runBlockingTest {
            coEvery { remoteDataSource.getBeers(PAGE) } returns listOf()
            val result = remoteKeyRemoteMediator.load(LoadType.REFRESH, pagingState)
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        }


    @Test
    fun `get beers list from remote and error occurred`() =
        testDispatcher.runBlockingTest {

            coEvery { remoteDataSource.getBeers(PAGE) } throws IOException()
            val result = remoteKeyRemoteMediator.load(LoadType.REFRESH, pagingState)
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)

        }

    @Test
    fun `get beers list by query successfully`() =
        testDispatcher.runBlockingTest {

            val filter = filters[0]
            coEvery { remoteDataSource.getBeersByQuery(filter) } returns listBeers.filter {
                it.name.contains(filter)
            }
            val remoteResult = remoteDataSource.getBeersByQuery(filter).map {
                it.toBeerDto()
            }
            val resultExpected = beersRepository.getBeersByQuery(filter).last()
            Assert.assertTrue(resultExpected.isNotEmpty())
            Assert.assertEquals(resultExpected, remoteResult)

        }


    @Test
    fun `get beers list by query not result`() =
        testDispatcher.runBlockingTest {

            val filter = filters[1]
            coEvery { remoteDataSource.getBeersByQuery(filter) } returns listBeers.filter {
                it.name.contains(filter)
            }
            val remoteResult = remoteDataSource.getBeersByQuery(filter).map { it.toBeerDto() }
            val resultExpected = beersRepository.getBeersByQuery(filter).last()
            Assert.assertTrue(resultExpected.isEmpty())
            Assert.assertEquals(resultExpected, remoteResult)

        }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


}