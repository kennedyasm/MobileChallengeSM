package com.challenges.mobilechallengesm.data.local.source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenges.mobilechallengesm.core.App
import com.challenges.mobilechallengesm.data.local.BeersDataBase
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest {

    lateinit var beersDao: BeersDao
    lateinit var remoteKeyDao: RemoteKeyDao
    lateinit var dataBase: BeersDataBase
    lateinit var localDataSource: LocalDataSource
    private val entityTest = RemoteKeyEntity(0, 1, 2, false)
    private val entityTest2 = RemoteKeyEntity(1, 1, 2, false)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<App>()
        dataBase = Room.inMemoryDatabaseBuilder(
            context, BeersDataBase::class.java
        ).build()

        beersDao = dataBase.beersDao()
        remoteKeyDao = dataBase.remoteKeyDao()
        localDataSource = LocalDataSourceImpl(remoteKeyDao, beersDao)
    }

    @Test
    fun insertAndGetRemoteKey() {
        runBlocking {
            localDataSource.insertOrReplaceRemoteKey(entityTest)
            val expected = localDataSource.getRemoteKey().firstOrNull()
            Assert.assertTrue(expected != null)
            Assert.assertEquals(expected, entityTest)
        }
    }

    @Test
    fun insertOrReplaceAndGetRemoteKey() {
        runBlocking {
            localDataSource.insertOrReplaceRemoteKey(entityTest)
            localDataSource.insertOrReplaceRemoteKey(entityTest)
            val expected = localDataSource.getRemoteKey()
            Assert.assertTrue(expected.size == 1)
        }
    }


    @Test
    fun clearAllRemoteKey() {
        runBlocking {
            localDataSource.insertOrReplaceRemoteKey(entityTest)
            localDataSource.insertOrReplaceRemoteKey(entityTest2)
            val currentList = localDataSource.getRemoteKey()
            Assert.assertTrue(currentList.isNotEmpty())
            Assert.assertTrue(currentList.size == 2)
            localDataSource.clearAllRemoteKeys()
            val currentList2 = localDataSource.getRemoteKey()
            Assert.assertTrue(currentList2.isNullOrEmpty())

        }
    }


    @After
    fun closeDb() {
        dataBase.close()
    }
}