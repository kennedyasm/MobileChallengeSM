package com.challenges.mobilechallengesm.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.challenges.mobilechallengesm.data.local.dao.BeersDao
import com.challenges.mobilechallengesm.data.local.dao.RemoteKeyDao
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity
import com.challenges.mobilechallengesm.data.remote.source.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class PageKeyRemoteMediator constructor(
    private val remoteDataSource: RemoteDataSource,
    private val beersDao: BeersDao,
    private val remoteKeyDao: RemoteKeyDao
) : RemoteMediator<Int, BeerEntity>() {

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {

        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> remoteKeyDao.getRemoteKey().firstOrNull()
            }

            loadKey?.let {
                if (it.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = loadKey?.nextKey ?: STARTING_PAGE_INDEX
            val response = remoteDataSource.getBeers(page = page.toString())

            val endOfPaginationReached = response.isNullOrEmpty()
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            if (loadType == LoadType.REFRESH) {
                beersDao.clearAll()
                remoteKeyDao.clearAll()
            }

            remoteKeyDao.insertOrReplace(
                RemoteKeyEntity(
                    0,
                    prevKey = prevKey,
                    nextKey = nextKey,
                    isEndReached = endOfPaginationReached
                )
            )

            beersDao.insertAll(response.map { it.toBeerEntity() })

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}