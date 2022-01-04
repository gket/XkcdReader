package com.gketdev.xkcdreader.data.source

import com.gketdev.xkcdreader.data.database.dao.FavoriteDao
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: FavoriteDao) {

    suspend fun getAllFavoritedItems() = flow<List<XkcdEntity>> {
        withContext(Dispatchers.IO) {
            dao.getFavorites().catch {
                emit(listOf())
            }.collect {
                emit(it)
            }
        }
    }

    suspend fun isItemFavorited(id: Int): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext dao.isItemInFav(id) != null
        }


    suspend fun addFavorite(item: XkcdEntity) {
        withContext(Dispatchers.IO) {
            dao.addToFavorite(item)
        }
    }

    suspend fun deletFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deleteFavorite(id)
        }
    }


}