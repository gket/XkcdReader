package com.gketdev.xkcdreader.data.repository

import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.data.source.RemoteDataSource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    HomeRepository {
    override suspend fun getXkcdItem(id: Int?): XkcdResponse {
        return if (id == null)
            remoteDataSource.getLatestItem()
        else
            remoteDataSource.getItemById(id)
    }
}