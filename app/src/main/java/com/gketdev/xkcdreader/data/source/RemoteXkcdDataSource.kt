package com.gketdev.xkcdreader.data.source

import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.data.service.XkcdApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteXkcdDataSource @Inject constructor(
    private val xkcdApi: XkcdApi
) {
    suspend fun getLatestItem(): XkcdResponse =
        withContext(Dispatchers.IO) {
            xkcdApi.getLatestItem()
        }

    suspend fun getItemById(id:Int): XkcdResponse =
        withContext(Dispatchers.IO) {
            xkcdApi.getItemById(id)
        }
}

