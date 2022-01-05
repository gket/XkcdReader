package com.gketdev.xkcdreader.domain

import app.cash.turbine.test
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetItemUseCaseTest {

    private lateinit var SUT: GetItemUseCase

    private lateinit var repositoryImpl: XkcdRepository

    private val xkcdResponseItem = XkcdResponse(
        num = 0,
        alt = "",
        title = "",
        month = "",
        year = "",
        day = "",
        transcript = "",
        link = "",
        img = "",
        safe_title = "",
        news = ""
    )

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = GetItemUseCase(repositoryImpl)
    }

    @Test
    fun `when data is correct should return success`() = runBlocking {
        coEvery {
            repositoryImpl.getXkcdItem(any())
        } returns flow<XkcdResponse> {
            emit(xkcdResponseItem)
        }

        val data = SUT.invoke(0)

        data.test {
            val awaitItem = awaitItem()
            assert(awaitItem is DataResultState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

}