package com.challenges.mobilechallengesm.data.remote.source

import com.challenges.mobilechallengesm.BuildConfig
import com.challenges.mobilechallengesm.data.remote.model.BeerItem
import com.challenges.mobilechallengesm.data.remote.service.BeersApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RemoteDataSourceImplTest {

    private val mockWebServer = MockWebServer()
    private lateinit var client: OkHttpClient

    @MockK(relaxed = true)
    private lateinit var beersApi: BeersApi

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: RemoteDataSource

    private val errorMessage = "error server"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        client = spyk(
            OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS).writeTimeout(1, TimeUnit.SECONDS)
                .build()
        )
        beersApi = spyk(
            Retrofit.Builder().baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(BeersApi::class.java)
        )
        remoteDataSource = spyk(RemoteDataSourceImpl(beersApi))

    }

    @Test
    fun `get beers from server with 200 code successfully`() = runBlocking {
        var ex: Exception? = null
        var expected: List<BeerItem>? = null
        try {
            expected = remoteDataSource.getBeers("1")
        } catch (e: Exception) {
            ex = e
        }


        Assert.assertTrue(ex == null)
        Assert.assertTrue(expected?.isNotEmpty() == true)
        Assert.assertFalse(expected?.isNullOrEmpty() == true)

    }


    @Test
    fun `get beers from server list response code 400`() = runBlocking {
        var ex: Exception? = null
        try {
            remoteDataSource.getBeers("-100")
        } catch (e: Exception) {
            ex = e
        }
        Assert.assertFalse(ex == null)
        Assert.assertTrue(ex is HttpException)
        Assert.assertTrue((ex as HttpException).code() == 400)
    }


    @Test
    fun `get beers from server null list`() =
        runBlocking {
            coEvery { remoteDataSource.getBeers("1") } returns listOf()
            val expected = remoteDataSource.getBeers("1")
            Assert.assertTrue(expected.isNullOrEmpty())
        }


    @Test
    fun `get beers from server list throwing some exception`() =
        runBlocking {
            coEvery { remoteDataSource.getBeers("1") } throws IOException(errorMessage)
            var ex: Exception? = null
            try {
                remoteDataSource.getBeers("1")
            } catch (e: Exception) {
                ex = e
            }
            Assert.assertFalse(ex == null)
            Assert.assertTrue(ex?.message == errorMessage)
        }


    @Test
    fun `get beers by query from server with 200 code successfully`() =
        runBlocking {

            var expected: List<BeerItem>? = null
            var ex: Exception? = null
            try {
                expected = remoteDataSource.getBeersByQuery("a")
            } catch (e: Exception) {
                ex = e
            }

            Assert.assertTrue(ex == null)
            Assert.assertTrue(expected?.isNotEmpty() == true)
            Assert.assertFalse(expected.isNullOrEmpty())
        }

    @Test
    fun `get beers by query from server list response code 400`() = runBlocking {
        var ex: Exception? = null
        try {
            remoteDataSource.getBeers("")
        } catch (e: Exception) {
            ex = e
        }
        Assert.assertFalse(ex == null)
        Assert.assertTrue(ex is HttpException)
        Assert.assertTrue((ex as HttpException).code() == 400)
    }


    @Test
    fun `get beers by query from server null list`() = runBlocking {
        coEvery { remoteDataSource.getBeersByQuery("a") } returns listOf()
        val expected = remoteDataSource.getBeersByQuery("a")
        Assert.assertTrue(expected.isNullOrEmpty())
    }


    @Test
    fun `get beers by query from server list throwing some exception`() = runBlocking {
        coEvery { remoteDataSource.getBeersByQuery("a") } throws IOException(errorMessage)
        var ex: Exception? = null
        try {
            remoteDataSource.getBeersByQuery("a")
        } catch (e: Exception) {
            ex = e
        }
        Assert.assertFalse(ex == null)
        Assert.assertTrue(ex?.message == errorMessage)
    }


    @After()
    fun tearDown() {
        mockWebServer.shutdown()
    }

}
