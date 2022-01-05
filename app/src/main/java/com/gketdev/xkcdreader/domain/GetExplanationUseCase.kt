package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.ExplanationResponse
import javax.inject.Inject

class GetExplanationUseCase @Inject constructor(
    private val repository: XkcdRepository
) : GenericUseCase<Pair<Int, String>, ExplanationResponse> {
    override suspend fun invoke(param: Pair<Int, String>): ExplanationResponse {
        return repository.getExplanationItem(param.first, param.second)
    }
}