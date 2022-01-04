package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import kotlinx.coroutines.flow.Flow

interface GetItemUseCase {
    suspend operator fun invoke(id: Int?): Flow<DataResultState<XkcdResponse>>
}