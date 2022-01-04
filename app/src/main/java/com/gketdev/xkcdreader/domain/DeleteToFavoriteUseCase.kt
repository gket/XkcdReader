package com.gketdev.xkcdreader.domain

import javax.inject.Inject

class DeleteToFavoriteUseCase @Inject constructor(
    private val repository: XkcdRepository
) : GenericUseCase<Int, Unit> {
    override suspend fun invoke(param: Int) {
        repository.deleteItemFavorite(param)
    }
}