package com.gketdev.xkcdreader.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class IsItemFavoriteUseCaseTest {

    private lateinit var SUT: IsItemFavoriteUseCase

    private lateinit var repositoryImpl: XkcdRepository

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = IsItemFavoriteUseCase(repositoryImpl)
    }

    @Test
    fun `when item is in favorite list it should return true`() = runBlocking {
        coEvery {
            repositoryImpl.isItemInFavorited(any())
        } returns true
        val data = SUT.invoke(0)
        assert(data)
    }

    @Test
    fun `when item is not in favorite list it should return false`() = runBlocking {
        coEvery {
            repositoryImpl.isItemInFavorited(any())
        } returns false
        val data = SUT.invoke(0)
        assert(!data)
    }
}