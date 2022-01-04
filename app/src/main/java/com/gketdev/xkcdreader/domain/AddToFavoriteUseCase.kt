package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val repository: XkcdRepository) :
    GenericUseCase<XkcdEntity, Unit> {
    override suspend fun invoke(param: XkcdEntity) {
        repository.addItemFavorite(param)
    }
}