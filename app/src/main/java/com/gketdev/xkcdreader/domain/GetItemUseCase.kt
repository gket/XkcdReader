package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import dagger.Module
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface GetItemUseCase {
    suspend operator fun invoke(): Flow<DataResultState<XkcdResponse>>
}