package com.gketdev.xkcdreader.data.service

import com.gketdev.xkcdreader.data.model.XkcdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface XkcdApi {

    @GET("info.0.json")
    suspend fun getLatestItem(): XkcdResponse

    @GET("{id}/info.0.json")
    suspend fun getItemById(@Path("id") id: Int): XkcdResponse


}