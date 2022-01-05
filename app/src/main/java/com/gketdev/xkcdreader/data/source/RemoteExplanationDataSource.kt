package com.gketdev.xkcdreader.data.source

import com.gketdev.xkcdreader.data.model.ExplanationResponse
import com.gketdev.xkcdreader.data.service.ExplanationApi
import javax.inject.Inject

class RemoteExplanationDataSource @Inject constructor(
    private val explanationApi: ExplanationApi
) {

    suspend fun getExplanationData(num: Int, title: String): ExplanationResponse {
        val concattedTitle = title.replace(" ", "_")
        val text = "$num:_$concattedTitle"
        return explanationApi.getExplanation(id = text)
    }

}