package com.gketdev.xkcdreader.data.database.dao

import androidx.room.*
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM xkcditems")
    fun getFavorites(): Flow<List<XkcdEntity>>

    @Query("SELECT * FROM xkcditems WHERE xkcdId = :id")
    fun isItemInFav(id: Int): XkcdEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(xkcdEntity: XkcdEntity)

    @Query("DELETE FROM xkcditems WHERE xkcdId = :id")
    suspend fun deleteFavorite(id: Int)
}