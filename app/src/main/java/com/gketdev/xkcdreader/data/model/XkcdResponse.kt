package com.gketdev.xkcdreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XkcdResponse(
    @Json(name = "alt")
    val alt: String,
    @Json(name = "day")
    val day: String,
    @Json(name = "img")
    val img: String,
    @Json(name = "link")
    val link: String,
    @Json(name = "month")
    val month: String,
    @Json(name = "news")
    val news: String,
    @Json(name = "num")
    val num: Int,
    @Json(name = "safe_title")
    val safe_title: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "transcript")
    val transcript: String,
    @Json(name = "year")
    val year: String
)