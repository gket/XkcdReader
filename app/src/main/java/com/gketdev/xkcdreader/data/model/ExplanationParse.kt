package com.gketdev.xkcdreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExplanationParse(
    @Json(name = "title")
    val title: String,
    @Json(name = "pageid")
    val pageId: Int,
    @Json(name = "text")
    val text: ExplanationText,
)
