package com.gketdev.xkcdreader.data.service

import com.gketdev.xkcdreader.data.model.ExplanationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExplanationApi {

    @GET("api.php/")
    suspend fun getExplanation(
        @Query("action") parse: String = "parse",
        @Query("page") id: String,
        @Query("prop") prop: String = "text",
        @Query("sectiontitle") title: String = "Explanation",
        @Query("format") format: String = "json"
    ): ExplanationResponse

}