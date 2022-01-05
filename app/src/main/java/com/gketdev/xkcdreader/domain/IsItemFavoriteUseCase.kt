package com.gketdev.xkcdreader.domain

import javax.inject.Inject

class IsItemFavoriteUseCase @Inject constructor(private val repository: XkcdRepository) :
    GenericUseCase<Int, Boolean> {
    override suspend fun invoke(id: Int): Boolean = repository.isItemInFavorited(id)
}