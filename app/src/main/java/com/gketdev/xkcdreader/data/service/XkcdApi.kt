package com.gketdev.xkcdreader.data.service

import com.gketdev.xkcdreader.data.model.XkcdResponse
import retrofit2.http.GET

interface XkcdApi {

    @GET("info.0.json")
    suspend fun getLatestItem(): XkcdResponse


}