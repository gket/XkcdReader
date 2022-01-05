package com.gketdev.xkcdreader.domain

import app.cash.turbine.test
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetFavoriteItemsUseCaseTest {

    private lateinit var SUT: GetFavoriteItemsUseCase

    private lateinit var repositoryImpl: XkcdRepository

    private val xkcdEntity = XkcdEntity(
        xkcdId = 1,
        title = "Aaa",
        alt = "bbb",
        image = ""
    )

    private val favList = listOf(xkcdEntity, xkcdEntity.copy(xkcdId = 2))

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = GetFavoriteItemsUseCase(repositoryImpl)
    }

    @Test
    fun `when data is correct should return success state`() = runBlocking {
        coEvery {
            repositoryImpl.getFavoriteItems()
        } returns flow<List<XkcdEntity>> {
            emit(favList)
        }

        val data = SUT.invoke(null)

        data.test {
            val awaitItem = awaitItem()
            assert(awaitItem is DataResultState.Success)
            assert((awaitItem as DataResultState.Success).data.size == 2)
            cancelAndIgnoreRemainingEvents()
        }
    }
}