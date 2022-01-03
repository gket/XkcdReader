package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.data.repository.HomeRepository
import com.gketdev.xkcdreader.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemUseCaseImpl @Inject constructor(private val repository: HomeRepository) :
    GetItemUseCase {
    override suspend fun invoke(): Flow<DataResultState<XkcdResponse>> = flow {
        try {
            val data = repository.getXkcdItem()
            emit(DataResultState.Success(data))
        } catch (e: Exception) {
            emit(DataResultState.Error(message = e.message.toString()))
        }
    }
}