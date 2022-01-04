package com.gketdev.xkcdreader.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExplanationText(
    @Json(name = "*")
    val description: String,
)
