package com.gketdev.xkcdreader.domain

interface GenericUseCase<P, T> {
    suspend operator fun invoke(param: P): T
}