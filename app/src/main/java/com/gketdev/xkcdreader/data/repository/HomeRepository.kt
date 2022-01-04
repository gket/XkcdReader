package com.gketdev.xkcdreader.data.repository

import com.gketdev.xkcdreader.data.model.XkcdResponse

interface HomeRepository {
    suspend fun getXkcdItem(id: Int?): XkcdResponse
}