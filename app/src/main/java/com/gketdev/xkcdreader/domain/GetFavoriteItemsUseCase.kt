package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.DataResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetFavoriteItemsUseCase @Inject constructor(private val repository: XkcdRepository) :
    FlowUseCase<Int?, DataResultState<List<XkcdEntity>>> {
    override suspend fun invoke(param: Int?): Flow<DataResultState<List<XkcdEntity>>> = flow {
        repository.getFavoriteItems()
            .catch {
                DataResultState.Error<XkcdEntity>(it.cause, 0, it.message.toString())
            }
            .collect {
                emit(DataResultState.Success(it))
            }
    }
}