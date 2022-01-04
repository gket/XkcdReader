package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.ExplanationResponse
import com.gketdev.xkcdreader.data.model.XkcdResponse
import kotlinx.coroutines.flow.Flow

interface XkcdRepository {
    suspend fun getXkcdItem(id: Int?): Flow<XkcdResponse>
    suspend fun isItemInFavorited(id: Int): Boolean
    suspend fun addItemFavorite(item: XkcdEntity)
    suspend fun deleteItemFavorite(id: Int)
    suspend fun getExplanationItem(id: Int, title: String): ExplanationResponse
    suspend fun getFavoriteItems(): Flow<List<XkcdEntity>>
}