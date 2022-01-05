package com.gketdev.xkcdreader.domain
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<P, T> {
    suspend operator fun invoke(param: P): Flow<T>
}