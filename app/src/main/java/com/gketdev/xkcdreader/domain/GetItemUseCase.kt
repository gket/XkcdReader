package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: XkcdRepository) :
    FlowUseCase<Int?, DataResultState<XkcdResponse>> {
    override suspend fun invoke(param: Int?): Flow<DataResultState<XkcdResponse>> = flow {
        repository.getXkcdItem(param)
            .catch {
                DataResultState.Error<XkcdResponse>(it.cause, 0, it.message.toString())
            }
            .collect {
                emit(DataResultState.Success(it))
            }
    }
}