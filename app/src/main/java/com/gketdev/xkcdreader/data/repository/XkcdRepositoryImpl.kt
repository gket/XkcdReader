package com.gketdev.xkcdreader.data.repository

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.ExplanationResponse
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.data.source.LocalDataSource
import com.gketdev.xkcdreader.data.source.RemoteExplanationDataSource
import com.gketdev.xkcdreader.data.source.RemoteXkcdDataSource
import com.gketdev.xkcdreader.domain.XkcdRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class XkcdRepositoryImpl @Inject constructor(
    private val remoteXkcdDataSource: RemoteXkcdDataSource,
    private val remoteExplanationDataSource: RemoteExplanationDataSource,
    private val localDataSource: LocalDataSource
) : XkcdRepository {
    override suspend fun getXkcdItem(id: Int?): Flow<XkcdResponse> = flow {
        if (id == null)
            emit(remoteXkcdDataSource.getLatestItem())
        else
            emit(remoteXkcdDataSource.getItemById(id))
    }

    override suspend fun isItemInFavorited(id: Int): Boolean = localDataSource.isItemFavorited(id)


    override suspend fun addItemFavorite(item: XkcdEntity) {
        localDataSource.addFavorite(item)
    }

    override suspend fun deleteItemFavorite(id: Int) {
        localDataSource.deletFavorite(id)
    }

    override suspend fun getExplanationItem(id: Int, title: String): ExplanationResponse {
        return remoteExplanationDataSource.getExplanationData(id, title)
    }

}